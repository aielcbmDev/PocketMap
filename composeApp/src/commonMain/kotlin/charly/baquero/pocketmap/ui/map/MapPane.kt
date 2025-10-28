package charly.baquero.pocketmap.ui.map

import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.ui.LocationsViewState
import charly.baquero.pocketmap.ui.model.LocationModel

@Composable
fun MapPane(
    locationsViewState: LocationsViewState
) {
    val (locationList, locationSelected) = retrieveData(locationsViewState)
    MapComponent(
        locationList = locationList,
        locationSelected = locationSelected,
        onMarkerClick = { locationModel ->
            println("Marker clicked: ${locationModel.title}")
        }
    )
}

private fun retrieveData(
    locationsViewState: LocationsViewState
): Pair<List<LocationModel>?, LocationModel?> {
    return when (locationsViewState) {
        is LocationsViewState.Success -> Pair(
            locationsViewState.locationList,
            locationsViewState.locationSelected
        )

        else -> Pair(null, null)
    }
}
