import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tornadofx.asyncItems
import tornadofx.observableListOf
import java.net.URL
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

val earthquakes = observableListOf<Properties>()
val formatter: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT
val today: LocalDate = LocalDate.now()

// Query all available events with parameters
var urlQuery =
    "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=$today&limit=20000"

/**
 * Properties of an earthquake events
 *
 * @property mag - Magnitude
 * @property place - place
 * @property time
 * @property type
 * @property title
 * @constructor Create empty Properties
 *
 * You can add more properties when needed
 */
@Serializable
data class Properties(
    val mag: Double?,
    val place: String?,
    val time: Long,
    val type: String,
    val title: String
) {
    @Contextual
    val date: String = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()).format(
        DateTimeFormatter.ofPattern("dd-MM-yyyy")
    )

    @Contextual
    val time2: String = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()).format(
        DateTimeFormatter.ofPattern("HH:mm")
    )
    @Contextual
    val state: String? = place?.substringAfter(", ")
}

/**
 * Feature - describes one earthquake event
 *
 * @property type = "Feature"
 * @property properties
 * @constructor Create empty Feature
 *
 */
@Serializable
data class Feature(
    val type: String,
    val properties: Properties
)

/**
 * Feature collection - Collection of earthquake events
 *
 * @property type = "FeatureCollection"
 * @property features - Array of earthquake events
 * @constructor Create empty Feature collection
 *
 */
@Serializable
data class FeatureCollection(
    val type: String,
    val features: Array<Feature>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FeatureCollection

        if (type != other.type) return false
        if (!features.contentEquals(other.features)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + features.contentHashCode()
        return result
    }
}

/**
 *
 * A function which gets the earthquake date via an API call.
 * The query for the API call is passed as an argument.
 * The retrieved data is returned as an FeatureCollection which itself is a serializable data class.
 *
 */
fun getEarthQuakes(url: String): FeatureCollection {
    return try {
        val jsonString = URL(url).readText()
        val json = Json { ignoreUnknownKeys = true }
        json.decodeFromString<FeatureCollection>(jsonString)
    } catch (e: Exception) {
        FeatureCollection("Error", arrayOf(Feature("Error", Properties(0.0, "Error",0, "Error","Error"))))
    }
}

/**
 *
 *This function calls the getEarthQuakes function and maps the retrieved data asynchronously.
 *
 */
fun createEarthQuakeList(url: String = urlQuery) {
    try {
        earthquakes.asyncItems {
            getEarthQuakes(url).features.map {
                it.properties
            }
        }
    } catch (e: Exception) {
        println(e)
    }
}

/**
 *
 * This Function calls the createEarthQuakeList function after 5s to get new Data in fixed time frames.
 *
 */
fun updateList() {
    try {
        Timer().scheduleAtFixedRate( object : TimerTask() {
            override fun run() {
                createEarthQuakeList()
            }
        }, 0, 5000)
    } catch (e: Exception) {
        println(e)
    }
}
