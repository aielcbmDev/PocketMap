package charly.baquero.pocketmap.ui.map

import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.domain.model.Location
import charly.baquero.pocketmap.ui.LocationsViewState

@Composable
fun MapPane(
    locationsViewState: LocationsViewState
) {
    val (locationList, locationSelected) = retrieveData(locationsViewState)
    MapComponent(
        locationList = locationList,
        locationSelected = locationSelected
    )
}

private fun retrieveData(
    locationsViewState: LocationsViewState
): Pair<List<Location>?, Location?> {
    return when (locationsViewState) {
        is LocationsViewState.Success -> Pair(
            locationsViewState.locationList,
            locationsViewState.locationSelected
        )

        else -> Pair(null, null)
    }
}
