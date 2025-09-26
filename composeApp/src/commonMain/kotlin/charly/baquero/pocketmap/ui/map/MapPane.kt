package charly.baquero.pocketmap.ui.map

import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.domain.model.Location
import charly.baquero.pocketmap.ui.DisplayGroupViewState
import charly.baquero.pocketmap.ui.DisplayLocationsViewState

@Composable
fun MapPane(
    displayGroupState: DisplayGroupViewState
) {
    val (locationList, locationSelected) = retrieveData(displayGroupState)
    MapComponent(
        locationList = locationList,
        locationSelected = locationSelected
    )
}

private fun retrieveData(
    displayGroupState: DisplayGroupViewState
): Pair<List<Location>?, Location?> {
    return when (displayGroupState) {
        is DisplayGroupViewState.Success -> {
            when (val locationsState = displayGroupState.displayLocationsViewState) {
                is DisplayLocationsViewState.Success -> Pair(
                    locationsState.locationList,
                    locationsState.locationSelected
                )

                else -> Pair(null, null)
            }
        }

        else -> Pair(null, null)
    }
}
