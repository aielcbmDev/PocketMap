package charly.baquero.pocketmap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import charly.baquero.pocketmap.ui.model.GroupModel
import charly.baquero.pocketmap.ui.model.LocationModel
import charly.baquero.pocketmap.ui.utils.mapToGroupModelList
import charly.baquero.pocketmap.ui.utils.mapToLocationModelList
import com.charly.domain.usecases.add.AddGroupUseCase
import com.charly.domain.usecases.get.GetAllGroupsUseCase
import com.charly.domain.usecases.get.GetAllLocationsForGroupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val getAllLocationsForGroupUseCase: GetAllLocationsForGroupUseCase,
    private val addGroupUseCase: AddGroupUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        MainViewState(
            viewState = null,
            groupViewState = GroupViewState.Loading,
            locationsViewState = LocationsViewState.NoGroupSelected
        )
    )
    val state: StateFlow<MainViewState> = _state

    init {
        fetchAllGroups()
    }

    fun onClearMapClick() {
        _state.update { state ->
            state.copy(
                locationsViewState = LocationsViewState.NoGroupSelected
            )
        }
    }

    fun onLocationClick(location: LocationModel) {
        _state.update { state ->
            state.copy(
                locationsViewState = state.locationsViewState.getOnLocationClickViewState(
                    location = location
                )
            )
        }
    }

    fun fetchAllGroups(updateGroupData: Boolean = false) {
        if (_state.value.groupViewState !is GroupViewState.Success || updateGroupData) {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(groupViewState = GroupViewState.Loading)
                }
                try {
                    val groupList = getAllGroupsUseCase.execute().mapToGroupModelList()
                    if (groupList.isEmpty()) {
                        _state.update { state ->
                            state.copy(groupViewState = GroupViewState.Empty)
                        }
                    } else {
                        _state.update { state ->
                            state.copy(
                                groupViewState = GroupViewState.Success(
                                    groupList = groupList
                                )
                            )
                        }
                    }
                } catch (_: Exception) {
                    _state.update { state ->
                        state.copy(groupViewState = GroupViewState.Error)
                    }
                }
            }
        }
    }

    fun fetchLocationsForGroup(group: GroupModel) {
        viewModelScope.launch {
            setLocationsLoading(group)
            try {
                val locationList =
                    getAllLocationsForGroupUseCase.execute(group.id).mapToLocationModelList()
                setLocationsForGroup(group, locationList)
            } catch (_: Exception) {
                setLocationsError(group)
            }
        }
    }

    fun displayGroupOptionsMenu(group: GroupModel) {
        _state.update { state ->
            val currentGroupViewState = state.groupViewState as? GroupViewState.Success
            currentGroupViewState?.let {
                state.copy(
                    groupViewState = it.copy(
                        groupSelected = group,
                        displayOptionsMenu = true
                    )
                )
            } ?: run {
                state.copy(
                    groupViewState = GroupViewState.Error
                )
            }
        }
    }

    fun dismissGroupOptionsMenu() {
        _state.update { state ->
            val currentGroupViewState = state.groupViewState as? GroupViewState.Success
            currentGroupViewState?.let {
                state.copy(
                    groupViewState = it.copy(
                        groupSelected = null,
                        displayOptionsMenu = false
                    )
                )
            } ?: run {
                state.copy(
                    groupViewState = GroupViewState.Error
                )
            }
        }
    }

    private fun setLocationsForGroup(
        group: GroupModel,
        locationList: List<LocationModel>
    ) {
        _state.update { state ->
            state.copy(
                locationsViewState = LocationsViewState.Success(
                    groupName = group.name,
                    locationList = locationList
                )
            )
        }
    }

    private fun setLocationsLoading(group: GroupModel) {
        _state.update { state ->
            state.copy(
                locationsViewState = LocationsViewState.Loading(
                    groupName = group.name
                )
            )
        }
    }

    private fun setLocationsError(group: GroupModel) {
        _state.update { state ->
            state.copy(
                locationsViewState = LocationsViewState.Error
            )
        }
    }

    fun showCreateGroupDialog() {
        _state.update { state ->
            state.copy(
                viewState = ViewState.CreateGroupDialog()
            )
        }
    }

    fun dismissCreateGroupDialog() {
        _state.update { state ->
            state.copy(
                viewState = null
            )
        }
    }

    fun createGroup(groupName: String) {
        viewModelScope.launch {
            try {
                addGroupUseCase.execute(groupName)
                fetchAllGroups(updateGroupData = true)
                _state.update { state ->
                    state.copy(viewState = null)
                }
            } catch (_: Exception) {
                _state.update { state ->
                    state.copy(viewState = ViewState.CreateGroupDialog(displayError = true))
                }
            }
        }
    }
}

data class MainViewState(
    val viewState: ViewState? = null,
    val groupViewState: GroupViewState,
    val locationsViewState: LocationsViewState
)

sealed class ViewState {
    data class CreateGroupDialog(
        val displayError: Boolean = false
    ) : ViewState()
}

sealed interface GroupViewState {
    data object Empty : GroupViewState
    data object Loading : GroupViewState
    data class Success(
        val displayOptionsMenu: Boolean = false,
        val groupSelected: GroupModel? = null,
        val groupList: List<GroupModel>
    ) : GroupViewState

    data object Error : GroupViewState
}

sealed interface LocationsViewState {
    data object NoGroupSelected : LocationsViewState
    data class Empty(
        val groupName: String
    ) : LocationsViewState

    data class Loading(
        val groupName: String
    ) : LocationsViewState

    data class Success(
        val groupName: String,
        val locationList: List<LocationModel>,
        val locationSelected: LocationModel? = null
    ) : LocationsViewState

    data object Error : LocationsViewState
}
