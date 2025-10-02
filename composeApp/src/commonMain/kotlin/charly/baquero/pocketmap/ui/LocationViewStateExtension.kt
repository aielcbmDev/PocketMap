package charly.baquero.pocketmap.ui

import com.charly.domain.model.Location

fun LocationsViewState.getOnLocationClickViewState(
    location: Location
): LocationsViewState {
    val currentLocationsState = this as? LocationsViewState.Success
    return currentLocationsState?.copy(
        locationSelected = location
    ) ?: LocationsViewState.Error
}
