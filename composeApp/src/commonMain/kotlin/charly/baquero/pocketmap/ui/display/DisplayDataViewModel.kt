package charly.baquero.pocketmap.ui.display

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

class DisplayDataViewModel(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val getAllLocationsForGroupUseCase: GetAllLocationsForGroupUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DisplayGroupViewState>(DisplayGroupViewState.Loading)
    val state: StateFlow<DisplayGroupViewState> = _state

    init {
        displayAllGroups()
    }

    fun displayAllGroups() {
        viewModelScope.launch {
            _state.value = DisplayGroupViewState.Loading
            try {
                val groupList = getAllGroupsUseCase.execute()
                _state.value = DisplayGroupViewState.Success(
                    groupList = groupList,
                    displayLocationsViewState = DisplayLocationsViewState.NoGroupSelected
                )
            } catch (_: Exception) {
                _state.value = DisplayGroupViewState.Error
            }
        }
    }

    fun setSelectedGroup(group: Group) {
        displayLocationsForGroup(group)
    }

    private fun displayLocationsForGroup(group: Group) {
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
            val current = state as? DisplayGroupViewState.Success
            if (locationList.isEmpty()) {
                current?.copy(
                    displayLocationsViewState = DisplayLocationsViewState.Empty(group.name)
                ) ?: DisplayGroupViewState.Error
            } else {
                current?.copy(
                    displayLocationsViewState = DisplayLocationsViewState.Success(
                        groupName = group.name,
                        locationList = locationList
                    )
                ) ?: DisplayGroupViewState.Error
            }
        }
    }

    private fun setLocationsLoading(group: Group) {
        _state.update { state ->
            val current = state as? DisplayGroupViewState.Success
            current?.copy(
                displayLocationsViewState = DisplayLocationsViewState.Loading(group.name)
            ) ?: DisplayGroupViewState.Error
        }
    }

    private fun setLocationsError(group: Group) {
        _state.update { state ->
            val current = state as? DisplayGroupViewState.Success
            current?.copy(
                displayLocationsViewState = DisplayLocationsViewState.Error(group.name)
            ) ?: DisplayGroupViewState.Error
        }
    }
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
        val locationList: List<Location>
    ) : DisplayLocationsViewState

    data class Error(
        val groupName: String
    ) : DisplayLocationsViewState
}
