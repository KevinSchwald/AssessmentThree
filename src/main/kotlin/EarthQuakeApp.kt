import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY
import javafx.stage.Stage
import tornadofx.*
import java.time.LocalDate

class EarthQuakeApp : App(StartView::class) {
    override fun start(stage: Stage) {
        stage.width = 1000.0
        stage.height = 500.0
        super.start(stage)
    }
}

class StartView : View("EarthQuakeApp") {
    override val root = vbox {
        alignment = Pos.CENTER
        hbox {
            alignment = Pos.CENTER
            button("Start") {
                action {
                    try {
                        updateList()
                    } catch (e: Exception) {
                        label("Error")
                    } finally {
                        replaceWith<TableView>()
                    }
                }
            }
        }
    }
}

class TableView : View("EarthQuakeApp") {
    private val date = SimpleObjectProperty<LocalDate>()
    override val root = borderpane {
        top {
            vbox {
                hbox {
                    alignment = Pos.CENTER
                    label {
                        bind(Bindings.concat("Number of earthquakes: ", Bindings.size(earthquakes)))
                    }
                }
                hbox {
                    alignment = Pos.CENTER
                    label {
                        text = "Datum: "
                    }
                    datepicker(date) {
                        value = LocalDate.now()
                        setOnAction { urlQuery = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=${date.value.atStartOfDay()}&endtime=${"${date.value}T23:59"}&limit=20000" }
                    }
                }
            }
        }
        center {
            tableview(earthquakes) {
                readonlyColumn("Title:", Properties::title)
                readonlyColumn("State:", Properties::state)
                readonlyColumn("Date:", Properties::date)
                readonlyColumn("Time:", Properties::time2)
                readonlyColumn("Magnitude", Properties::mag)
                columnResizePolicy = CONSTRAINED_RESIZE_POLICY
            }
        }
    }
}

fun main(args: Array<String>) {
    launch<EarthQuakeApp>(args)
}