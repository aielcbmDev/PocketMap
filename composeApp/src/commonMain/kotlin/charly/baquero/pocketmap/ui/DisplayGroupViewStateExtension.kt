package charly.baquero.pocketmap.ui

import charly.baquero.pocketmap.domain.model.Location

fun DisplayGroupViewState.getOnClearMapClickViewState(): DisplayGroupViewState {
    val current = this as? DisplayGroupViewState.Success
    return current?.copy(
        displayLocationsViewState = DisplayLocationsViewState.NoGroupSelected
    ) ?: DisplayGroupViewState.Error
}

fun DisplayGroupViewState.getOnLocationClickViewState(
    location: Location
): DisplayGroupViewState {
    val current = this as? DisplayGroupViewState.Success
    return current?.let {
        val currentLocationsState =
            it.displayLocationsViewState as? DisplayLocationsViewState.Success
        currentLocationsState?.let { locationsState ->
            it.copy(
                displayLocationsViewState = DisplayLocationsViewState.Success(
                    groupName = locationsState.groupName,
                    locationList = locationsState.locationList,
                    locationSelected = location
                )
            )
        }
    } ?: DisplayGroupViewState.Error
}


fun DisplayGroupViewState.getLocationViewState(
    groupName: String,
    locationList: List<Location>
): DisplayGroupViewState {
    val current = this as? DisplayGroupViewState.Success
    return if (locationList.isEmpty()) {
        current?.copy(
            displayLocationsViewState = DisplayLocationsViewState.Empty(groupName)
        ) ?: DisplayGroupViewState.Error
    } else {
        current?.copy(
            displayLocationsViewState = DisplayLocationsViewState.Success(
                groupName = groupName,
                locationList = locationList
            )
        ) ?: DisplayGroupViewState.Error
    }
}

fun DisplayGroupViewState.getLoadingLocationViewState(
    groupName: String
): DisplayGroupViewState {
    val current = this as? DisplayGroupViewState.Success
    return current?.copy(
        displayLocationsViewState = DisplayLocationsViewState.Loading(groupName)
    ) ?: DisplayGroupViewState.Error
}

fun DisplayGroupViewState.getErrorLocationViewState(
    groupName: String
): DisplayGroupViewState {
    val current = this as? DisplayGroupViewState.Success
    return current?.copy(
        displayLocationsViewState = DisplayLocationsViewState.Error(groupName)
    ) ?: DisplayGroupViewState.Error
}
