package charly.baquero.pocketmap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import charly.baquero.pocketmap.domain.GetAllGroupsUseCase
import charly.baquero.pocketmap.domain.GetAllLocationsForGroupUseCase
import charly.baquero.pocketmap.domain.model.Group
import charly.baquero.pocketmap.domain.model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val getAllLocationsForGroupUseCase: GetAllLocationsForGroupUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        MainViewState(
            viewEvent = null,
            displayGroupViewState = DisplayGroupViewState.Loading
        )
    )
    val state: StateFlow<MainViewState> = _state

    init {
        fetchAllGroups()
    }

    fun onClearMapClick() {
        _state.update { state ->
            state.copy(
                displayGroupViewState = getOnClearMapClickViewState(
                    displayGroupViewState = state.displayGroupViewState
                )
            )
        }
    }

    private fun getOnClearMapClickViewState(
        displayGroupViewState: DisplayGroupViewState
    ): DisplayGroupViewState {
        val current = displayGroupViewState as? DisplayGroupViewState.Success
        return current?.copy(
            displayLocationsViewState = DisplayLocationsViewState.NoGroupSelected
        ) ?: DisplayGroupViewState.Error
    }

    fun onLocationClick(location: Location) {
        _state.update { state ->
            state.copy(
                displayGroupViewState = getOnLocationClickViewState(
                    displayGroupViewState = state.displayGroupViewState,
                    location = location
                )
            )
        }
    }

    private fun getOnLocationClickViewState(
        displayGroupViewState: DisplayGroupViewState,
        location: Location
    ): DisplayGroupViewState {
        val current = displayGroupViewState as? DisplayGroupViewState.Success
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

    fun fetchAllGroups(updateGroupData: Boolean = false) {
        if (_state.value.displayGroupViewState !is DisplayGroupViewState.Success || updateGroupData) {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(displayGroupViewState = DisplayGroupViewState.Loading)
                }
                try {
                    val groupList = getAllGroupsUseCase.execute()
                    if (groupList.isEmpty()) {
                        _state.update { state ->
                            state.copy(displayGroupViewState = DisplayGroupViewState.Empty)
                        }
                    } else {
                        _state.update { state ->
                            state.copy(
                                displayGroupViewState = DisplayGroupViewState.Success(
                                    groupList = groupList,
                                    displayLocationsViewState = DisplayLocationsViewState.NoGroupSelected
                                )
                            )
                        }
                    }
                } catch (_: Exception) {
                    _state.update { state ->
                        state.copy(displayGroupViewState = DisplayGroupViewState.Error)
                    }
                }
            }
        }
    }

    fun fetchLocationsForGroup(group: Group) {
        viewModelScope.launch {
            setLocationsLoading(group)
            try {
                val locationList = getAllLocationsForGroupUseCase.execute(group.id)
                setLocationsForGroup(group, locationList)
            } catch (_: Exception) {
                setLocationsError(group)
            }
        }
    }

    private fun setLocationsForGroup(
        group: Group,
        locationList: List<Location>
    ) {
        _state.update { state ->
            state.copy(
                displayGroupViewState = getLocationViewState(
                    displayGroupViewState = state.displayGroupViewState,
                    groupName = group.name,
                    locationList = locationList
                )
            )
        }
    }

    private fun getLocationViewState(
        displayGroupViewState: DisplayGroupViewState,
        groupName: String,
        locationList: List<Location>
    ): DisplayGroupViewState {
        val current = displayGroupViewState as? DisplayGroupViewState.Success
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

    private fun setLocationsLoading(group: Group) {
        _state.update { state ->
            state.copy(
                displayGroupViewState = getLoadingLocationViewState(
                    displayGroupViewState = state.displayGroupViewState,
                    groupName = group.name
                )
            )
        }
    }

    private fun getLoadingLocationViewState(
        displayGroupViewState: DisplayGroupViewState,
        groupName: String
    ): DisplayGroupViewState {
        val current = displayGroupViewState as? DisplayGroupViewState.Success
        return current?.copy(
            displayLocationsViewState = DisplayLocationsViewState.Loading(groupName)
        ) ?: DisplayGroupViewState.Error
    }

    private fun setLocationsError(group: Group) {
        _state.update { state ->
            state.copy(
                displayGroupViewState = getErrorLocationViewState(
                    displayGroupViewState = state.displayGroupViewState,
                    groupName = group.name
                )
            )
        }
    }

    private fun getErrorLocationViewState(
        displayGroupViewState: DisplayGroupViewState,
        groupName: String
    ): DisplayGroupViewState {
        val current = displayGroupViewState as? DisplayGroupViewState.Success
        return current?.copy(
            displayLocationsViewState = DisplayLocationsViewState.Error(groupName)
        ) ?: DisplayGroupViewState.Error
    }

    fun showCreateGroupDialog() {
        _state.update { state ->
            state.copy(
                viewEvent = ViewEvent.CreateGroupDialog
            )
        }
    }

    fun dismissCreateGroupDialog() {
        _state.update { state ->
            state.copy(
                viewEvent = null
            )
        }
    }
}

data class MainViewState(
    val viewEvent: ViewEvent? = null,
    val displayGroupViewState: DisplayGroupViewState,
)

sealed class ViewEvent {
    data object CreateGroupDialog : ViewEvent()
}

sealed interface DisplayGroupViewState {
    data object Empty : DisplayGroupViewState
    data object Loading : DisplayGroupViewState
    data class Success(
        val groupList: List<Group>,
        val displayLocationsViewState: DisplayLocationsViewState
    ) : DisplayGroupViewState

    data object Error : DisplayGroupViewState
}

sealed interface DisplayLocationsViewState {
    data object NoGroupSelected : DisplayLocationsViewState
    data class Empty(
        val groupName: String
    ) : DisplayLocationsViewState

    data class Loading(
        val groupName: String
    ) : DisplayLocationsViewState

    data class Success(
        val groupName: String,
        val locationList: List<Location>,
        val locationSelected: Location? = null
    ) : DisplayLocationsViewState

    data class Error(
        val groupName: String
    ) : DisplayLocationsViewState
}
