On the website https://earthquake.usgs.gov/fdsnws/event/1/ several APIs are described to get Earthquake information.
The API endpoint can be used to get the count or detailed information as geojson (format=geojson).
The data are updated when a new event happens.

The given code example (EarthQuake.kt) reads and parses the JSON structure with the Kotlinx API and converts it into Kotlin objects (use the following pom.xml to run the code).

Implement a TornadoFX UI that shows all earthquake events of the current day. The data must be retrieved online in an asynchronous way using Kotlin coroutines.
The data in the UI should be updated automatically (asynchronous in the background). The UI must stay reactive for user manipulations.

Upload your complete Maven project!!!

Points (max. 25):

Requested Functionality (max 18 Points)
Running TornadoFX Application (3 Points) using builder style (1 Point)
Display of earthquake data of the day in the UI (1 Point)
The data is shown in a user-friendly form (e.g. List or TableView) (3 Points)
The data are updated automatically  every time they change (5 Points)
The data is retrieved online and parsed (1 Point, you can use the given code)
data is retrieved asynchronously (2 Points)
Exceptions are handled correctly (1 Point)
Clear separation of model and view/controller (1 Point)
Additional Features (max. 3 Points), e.g.:
Graphical display of events in a certain timeframe,
Data can be saved in a file and restored,
Enhance the UI to make the section of different timeframe or locations possible, ...
Code style etc. (max 6 Points)
code style (no warnings, errors, TODOs or unused code) (1 Point),
Comments for all public components (1 Point)
Class/function design (1 Point),
Kotlin style (nullable and mutable types should be avoided, correct val/var usage, no Any? type, no global variables, ...), (2 Points)
functioning maven build (1 Point).
Individual task processing (no teamwork!)