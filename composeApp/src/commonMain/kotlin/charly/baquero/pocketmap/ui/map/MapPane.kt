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
        else -> {}
    }
}

@Composable
private fun MapPaneWithLocations(locationsState: DisplayLocationsViewState) {
    when (locationsState) {
        is DisplayLocationsViewState.Success -> MapComponent(locationsState.locationList)
        else -> MapComponent()
    }
}
