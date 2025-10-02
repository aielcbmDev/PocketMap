package charly.baquero.pocketmap.ui

import charly.baquero.pocketmap.ui.model.LocationModel

fun LocationsViewState.getOnLocationClickViewState(
    location: LocationModel
): LocationsViewState {
    val currentLocationsState = this as? LocationsViewState.Success
    return currentLocationsState?.copy(
        locationSelected = location
    ) ?: LocationsViewState.Error
}
