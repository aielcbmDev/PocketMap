package charly.baquero.pocketmap.ui.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import charly.baquero.pocketmap.domain.GetAllGroupsUseCase
import charly.baquero.pocketmap.domain.GetAllLocationsForGroupUseCase
import com.charly.database.model.groups.Group
import com.charly.database.model.locations.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DisplayGroupsViewModel(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val getAllLocationsForGroupUseCase: GetAllLocationsForGroupUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DisplayGroupsViewState>(DisplayGroupsViewState.Loading)
    val state: StateFlow<DisplayGroupsViewState> = _state

    init {
        displayAllGroups()
    }

    fun displayAllGroups() {
        viewModelScope.launch {
            _state.value = DisplayGroupsViewState.Loading
            try {
                val groupList = getAllGroupsUseCase.execute()
                _state.value = DisplayGroupsViewState.Success(
                    groupList = groupList,
                    displayLocationsViewState = DisplayLocationsViewState.NoGroupSelected
                )
            } catch (_: Exception) {
                _state.value = DisplayGroupsViewState.Error
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
            val current = state as? DisplayGroupsViewState.Success
            if (locationList.isEmpty()) {
                current?.copy(
                    displayLocationsViewState = DisplayLocationsViewState.Empty(group.name)
                ) ?: DisplayGroupsViewState.Error
            } else {
                current?.copy(
                    displayLocationsViewState = DisplayLocationsViewState.Success(
                        groupName = group.name,
                        locationList = locationList
                    )
                ) ?: DisplayGroupsViewState.Error
            }
        }
    }

    private fun setLocationsLoading(group: Group) {
        _state.update { state ->
            val current = state as? DisplayGroupsViewState.Success
            current?.copy(
                displayLocationsViewState = DisplayLocationsViewState.Loading(group.name)
            ) ?: DisplayGroupsViewState.Error
        }
    }

    private fun setLocationsError(group: Group) {
        _state.update { state ->
            val current = state as? DisplayGroupsViewState.Success
            current?.copy(
                displayLocationsViewState = DisplayLocationsViewState.Error(group.name)
            ) ?: DisplayGroupsViewState.Error
        }
    }
}

sealed interface DisplayGroupsViewState {
    data object Empty : DisplayGroupsViewState
    data object Loading : DisplayGroupsViewState
    data class Success(
        val groupList: List<Group>,
        val displayLocationsViewState: DisplayLocationsViewState
    ) : DisplayGroupsViewState

    data object Error : DisplayGroupsViewState
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
