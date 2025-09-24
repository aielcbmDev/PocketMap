package charly.baquero.pocketmap.ui.map

import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.ui.DisplayGroupViewState
import charly.baquero.pocketmap.ui.DisplayLocationsViewState

@Composable
fun MapPane(
    displayGroupState: DisplayGroupViewState,
) {
    when (val groupsState = displayGroupState) {
        is DisplayGroupViewState.Success -> MapPaneWithLocations(groupsState.displayLocationsViewState)
        else -> MapComponent()
    }
}

@Composable
private fun MapPaneWithLocations(locationsState: DisplayLocationsViewState) {
    when (locationsState) {
        is DisplayLocationsViewState.Success -> MapComponent(
            locationList = locationsState.locationList,
            locationSelected = locationsState.locationSelected
        )

        else -> MapComponent()
    }
}
