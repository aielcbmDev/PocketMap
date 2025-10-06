package charly.baquero.pocketmap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import charly.baquero.pocketmap.ui.model.GroupModel
import charly.baquero.pocketmap.ui.model.LocationModel
import charly.baquero.pocketmap.ui.utils.mapToGroupModelList
import charly.baquero.pocketmap.ui.utils.mapToLocationModelList
import com.charly.domain.usecases.add.AddGroupUseCase
import com.charly.domain.usecases.delete.DeleteGroupsUseCase
import com.charly.domain.usecases.edit.EditGroupUseCase
import com.charly.domain.usecases.get.GetAllGroupsUseCase
import com.charly.domain.usecases.get.GetAllLocationsForGroupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val getAllLocationsForGroupUseCase: GetAllLocationsForGroupUseCase,
    private val addGroupUseCase: AddGroupUseCase,
    private val deleteGroupsUseCase: DeleteGroupsUseCase,
    private val editGroupUseCase: EditGroupUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state

    init {
        onEvent(ViewEvent.FetchAllGroups)
    }

    fun onEvent(viewEvent: ViewEvent) {
        when (viewEvent) {
            is ViewEvent.ClearMap -> onClearMapClick()
            is ViewEvent.LocationClick -> onLocationClick(viewEvent.location)
            is ViewEvent.FetchAllGroups -> fetchAllGroups()
            is ViewEvent.FetchLocationsForGroup -> fetchLocationsForGroup(viewEvent.group)
            is ViewEvent.DisplayGroupOptionsMenu -> displayGroupOptionsMenu(viewEvent.group)
            is ViewEvent.DismissGroupOptionsMenu -> dismissGroupOptionsMenu()
            is ViewEvent.ShowCreateGroupDialog -> showCreateGroupDialog()
            is ViewEvent.ShowDeleteGroupsDialog -> showDeleteGroupDialog()
            is ViewEvent.ShowEditGroupDialog -> showEditGroupDialog()
            is ViewEvent.DismissDialog -> dismissDialog()
            is ViewEvent.CreateGroup -> createGroup(viewEvent.groupName)
            is ViewEvent.DeleteGroups -> deleteGroups()
            is ViewEvent.EditGroup -> editGroup(viewEvent.groupName)
        }
    }

    private fun onClearMapClick() {
        _state.update { it.copy(locationsViewState = LocationsViewState.NoGroupSelected) }
    }

    private fun onLocationClick(location: LocationModel) {
        _state.update { state ->
            val currentLocationsState = state.locationsViewState as? LocationsViewState.Success
            currentLocationsState?.let {
                state.copy(
                    locationsViewState = it.copy(locationSelected = location)
                )
            } ?: state
        }
    }

    private fun fetchAllGroups() {
        _state.update { it.copy(groupViewState = GroupViewState.Loading) }
        viewModelScope.launch {
            try {
                val groupList = getAllGroupsUseCase.execute().mapToGroupModelList()
                _state.update { state ->
                    val newGroupState = if (groupList.isEmpty()) {
                        GroupViewState.Empty
                    } else {
                        GroupViewState.Success(groupList = groupList)
                    }
                    state.copy(groupViewState = newGroupState)
                }
            } catch (_: Exception) {
                _state.update { it.copy(groupViewState = GroupViewState.Error) }
            }
        }
    }

    private fun fetchLocationsForGroup(group: GroupModel) {
        viewModelScope.launch {
            setLocationsLoading(group)
            try {
                val locationList =
                    getAllLocationsForGroupUseCase.execute(group.id).mapToLocationModelList()
                setLocationsForGroup(group, locationList)
            } catch (_: Exception) {
                _state.update { it.copy(locationsViewState = LocationsViewState.Error) }
            }
        }
    }

    private fun displayGroupOptionsMenu(group: GroupModel) {
        _state.update { state ->
            val currentGroupState = state.groupViewState as? GroupViewState.Success
            currentGroupState?.let {
                state.copy(
                    groupViewState = it.copy(
                        selectedGroupIds = handleSelectedGroupIds(it.selectedGroupIds, group)
                    )
                )
            } ?: state
        }
    }

    private fun handleSelectedGroupIds(
        oldMap: Map<Long, GroupModel>,
        group: GroupModel
    ): Map<Long, GroupModel> {
        val newMap = HashMap(oldMap)
        if (oldMap.contains(group.id)) {
            newMap.remove(group.id)
        } else {
            newMap.put(group.id, group)
        }
        return newMap
    }

    private fun dismissGroupOptionsMenu() {
        _state.update { state ->
            val currentGroupState = state.groupViewState as? GroupViewState.Success
            currentGroupState?.let {
                state.copy(
                    groupViewState = it.copy(selectedGroupIds = emptyMap())
                )
            } ?: state
        }
    }

    private fun setLocationsForGroup(
        group: GroupModel,
        locationList: List<LocationModel>
    ) {
        _state.update { state ->
            val newLocationState = if (locationList.isEmpty()) {
                LocationsViewState.Empty(group.name)
            } else {
                LocationsViewState.Success(
                    groupName = group.name,
                    locationList = locationList
                )
            }
            state.copy(locationsViewState = newLocationState)
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

    private fun showCreateGroupDialog() {
        _state.update { it.copy(dialogState = DialogState.CreateGroup()) }
    }

    private fun showDeleteGroupDialog() {
        _state.update { it.copy(dialogState = DialogState.DeleteGroups) }
    }

    private fun showEditGroupDialog() {
        _state.update { it.copy(dialogState = DialogState.EditGroup(getSingleSelectedGroup()?.name ?: "")) }
    }

    private fun dismissDialog() {
        _state.update { it.copy(dialogState = DialogState.NoDialog) }
    }

    private fun createGroup(groupName: String) {
        viewModelScope.launch {
            try {
                addGroupUseCase.execute(groupName)
                val groupList = getAllGroupsUseCase.execute().mapToGroupModelList()
                _state.update { state ->
                    val newGroupState = if (groupList.isEmpty()) {
                        GroupViewState.Empty
                    } else {
                        GroupViewState.Success(groupList = groupList)
                    }
                    state.copy(
                        dialogState = DialogState.NoDialog,
                        groupViewState = newGroupState
                    )
                }
            } catch (_: Exception) {
                _state.update { it.copy(dialogState = DialogState.CreateGroup(displayError = true)) }
            }
        }
    }

    private fun deleteGroups() {
        val currentGroupState = _state.value.groupViewState as? GroupViewState.Success ?: return
        _state.update {
            it.copy(groupViewState = currentGroupState.copy(deleteState = DeleteGroupState.Loading))
        }
        viewModelScope.launch {
            try {
                deleteGroupsUseCase.execute(currentGroupState.selectedGroupIds.keys)
                val groupList = getAllGroupsUseCase.execute().mapToGroupModelList()
                _state.update { state ->
                    val newGroupState = if (groupList.isEmpty()) {
                        GroupViewState.Empty
                    } else {
                        GroupViewState.Success(
                            groupList = groupList,
                            deleteState = DeleteGroupState.Success
                        )
                    }
                    state.copy(
                        dialogState = DialogState.NoDialog,
                        groupViewState = newGroupState
                    )
                }
            } catch (_: Exception) {
                _state.update {
                    it.copy(groupViewState = currentGroupState.copy(deleteState = DeleteGroupState.Error))
                }
            }
        }
    }

    private fun editGroup(groupName: String) {
        val currentGroupState = _state.value.groupViewState as? GroupViewState.Success ?: return
        _state.update {
            it.copy(groupViewState = currentGroupState.copy(editState = EditGroupState.Loading))
        }

        viewModelScope.launch {
            try {
                val selectedGroup = currentGroupState.selectedGroupIds.values.toList()[0]
                editGroupUseCase.execute(selectedGroup.id, groupName)
                val groupList = getAllGroupsUseCase.execute().mapToGroupModelList()
                _state.update { state ->
                    val newGroupState = if (groupList.isEmpty()) {
                        GroupViewState.Empty
                    } else {
                        GroupViewState.Success(
                            groupList = groupList,
                            deleteState = DeleteGroupState.Success
                        )
                    }
                    state.copy(
                        dialogState = DialogState.NoDialog,
                        groupViewState = newGroupState
                    )
                }
            } catch (_: Exception) {
                _state.update {
                    it.copy(groupViewState = currentGroupState.copy(deleteState = DeleteGroupState.Error))
                }
            }
        }
    }

    private fun getSingleSelectedGroup(): GroupModel? {
        val currentGroupState = _state.value.groupViewState as? GroupViewState.Success
        val list = currentGroupState?.selectedGroupIds?.values?.toList()
        return if (list?.isEmpty() == true) {
            null
        } else {
            return list?.get(0)
        }
    }
}

data class MainUiState(
    val groupViewState: GroupViewState = GroupViewState.Loading,
    val locationsViewState: LocationsViewState = LocationsViewState.NoGroupSelected,
    val dialogState: DialogState = DialogState.NoDialog,
)

sealed interface GroupViewState {
    data object Loading : GroupViewState
    data object Empty : GroupViewState
    data class Success(
        val groupList: List<GroupModel>,
        val selectedGroupIds: Map<Long, GroupModel> = emptyMap(),
        val deleteState: DeleteGroupState? = null,
        val editState: EditGroupState? = null
    ) : GroupViewState

    data object Error : GroupViewState
}

sealed interface DeleteGroupState {
    data object Loading : DeleteGroupState
    data object Success : DeleteGroupState
    data object Error : DeleteGroupState
}

sealed interface EditGroupState {
    data object Loading : EditGroupState
    data object Success : EditGroupState
    data object Error : EditGroupState
}

sealed interface LocationsViewState {
    data object NoGroupSelected : LocationsViewState
    data class Empty(val groupName: String) : LocationsViewState
    data class Loading(val groupName: String) : LocationsViewState
    data class Success(
        val groupName: String,
        val locationList: List<LocationModel>,
        val locationSelected: LocationModel? = null
    ) : LocationsViewState

    data object Error : LocationsViewState
}

sealed interface DialogState {
    data object NoDialog : DialogState
    data class CreateGroup(val displayError: Boolean = false) : DialogState
    data object DeleteGroups : DialogState
    data class EditGroup(val groupName: String) : DialogState
}

sealed interface ViewEvent {
    data object ClearMap : ViewEvent
    data class LocationClick(val location: LocationModel) : ViewEvent
    data object FetchAllGroups : ViewEvent
    data class FetchLocationsForGroup(val group: GroupModel) : ViewEvent
    data class DisplayGroupOptionsMenu(val group: GroupModel) : ViewEvent
    data object DismissGroupOptionsMenu : ViewEvent
    data object ShowCreateGroupDialog : ViewEvent
    data object ShowDeleteGroupsDialog : ViewEvent
    data object ShowEditGroupDialog : ViewEvent
    data object DismissDialog : ViewEvent
    data class CreateGroup(val groupName: String) : ViewEvent
    data object DeleteGroups : ViewEvent
    data class EditGroup(val groupName: String) : ViewEvent
}
