package charly.baquero.pocketmap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import charly.baquero.pocketmap.ui.model.GroupModel
import charly.baquero.pocketmap.ui.model.LocationModel
import charly.baquero.pocketmap.ui.utils.mapToGroupModelList
import charly.baquero.pocketmap.ui.utils.mapToLocationModelList
import com.charly.domain.usecases.database.add.AddGroupUseCase
import com.charly.domain.usecases.database.delete.DeleteGroupsUseCase
import com.charly.domain.usecases.database.edit.EditGroupUseCase
import com.charly.domain.usecases.database.get.GetAllGroupsUseCase
import com.charly.domain.usecases.database.get.GetAllLocationsForGroupUseCase
import com.charly.domain.usecases.networking.ReverseGeocodingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val getAllLocationsForGroupUseCase: GetAllLocationsForGroupUseCase,
    private val addGroupUseCase: AddGroupUseCase,
    private val deleteGroupsUseCase: DeleteGroupsUseCase,
    private val editGroupUseCase: EditGroupUseCase,
    private val reverseGeocodingUseCase: ReverseGeocodingUseCase
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
            is ViewEvent.OnMarkerClick -> {
                println("Marker clicked: ${viewEvent.locationModel.title}")
            }
        }
    }

    private fun onClearMapClick() {
        _state.update { it.copy(locationsViewState = LocationsViewState.NoGroupSelected) }
    }

    private fun onLocationClick(location: LocationModel) {
        _state.update { state ->
            (state.locationsViewState as? LocationsViewState.Success)?.let {
                state.copy(
                    locationsViewState = it.copy(locationSelected = location)
                )
            } ?: state.copy(locationsViewState = LocationsViewState.Error)
        }
    }

    private fun fetchAllGroups() {
        _state.update { it.copy(groupViewState = GroupViewState.Loading) }
        viewModelScope.launch {
            try {
                getAllGroupsUseCase.execute().map { it.mapToGroupModelList() }
                    .collect { groupList ->
                        _state.update { state ->
                            val newGroupState = if (groupList.isEmpty()) {
                                GroupViewState.Empty
                            } else {
                                GroupViewState.Success(groupList = groupList)
                            }
                            state.copy(groupViewState = newGroupState)
                        }
                    }
            } catch (_: Exception) {
                _state.update { it.copy(groupViewState = GroupViewState.Error) }
            }
        }
    }

    private fun fetchLocationsForGroup(group: GroupModel) {
        _state.update { it.copy(locationsViewState = LocationsViewState.Loading(groupName = group.name)) }
        viewModelScope.launch {
            try {
                getAllLocationsForGroupUseCase.execute(group.id).map { it.mapToLocationModelList() }
                    .collect { locationList ->
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
            } catch (_: Exception) {
                _state.update { it.copy(locationsViewState = LocationsViewState.Error) }
            }
        }
    }

    private fun displayGroupOptionsMenu(group: GroupModel) {
        _state.update { state ->
            (state.groupViewState as? GroupViewState.Success)?.let {
                state.copy(
                    groupViewState = it.copy(
                        selectedGroups = updateSelectedGroups(it.selectedGroups, group)
                    )
                )
            } ?: state.copy(groupViewState = GroupViewState.Error)
        }
    }

    private fun updateSelectedGroups(
        oldMap: Map<Long, GroupModel>,
        group: GroupModel
    ): Map<Long, GroupModel> {
        return if (oldMap.containsKey(group.id)) {
            oldMap.minus(group.id)
        } else {
            oldMap.plus((group.id to group))
        }
    }

    private fun dismissGroupOptionsMenu() {
        _state.update { state ->
            (state.groupViewState as? GroupViewState.Success)?.let {
                state.copy(
                    groupViewState = it.copy(selectedGroups = emptyMap())
                )
            } ?: state.copy(groupViewState = GroupViewState.Error)
        }
    }

    private fun showCreateGroupDialog() {
        _state.update { it.copy(dialogState = DialogState.CreateGroup()) }
    }

    private fun showDeleteGroupDialog() {
        _state.update { it.copy(dialogState = DialogState.DeleteGroups) }
    }

    private fun showEditGroupDialog() {
        _state.update { it.copy(dialogState = DialogState.EditGroup(getSingleSelectedGroupName())) }
    }

    private fun dismissDialog() {
        _state.update { it.copy(dialogState = DialogState.NoDialog) }
    }

    private fun createGroup(groupName: String) {
        viewModelScope.launch {
            try {
                addGroupUseCase.execute(groupName)
                _state.update { it.copy(dialogState = DialogState.NoDialog) }
            } catch (_: Exception) {
                _state.update { it.copy(dialogState = DialogState.CreateGroup(displayError = true)) }
            }
        }
    }

    private fun deleteGroups() {
        val currentGroupState = _state.value.groupViewState as? GroupViewState.Success
        if (currentGroupState == null) {
            _state.update { it.copy(groupViewState = GroupViewState.Error) }
            return
        }
        if (currentGroupState.selectedGroups.isEmpty()) {
            _state.update { it.copy(groupViewState = currentGroupState.copy(deleteGroupState = DeleteGroupState.Error)) }
            return
        }
        _state.update { it.copy(groupViewState = currentGroupState.copy(deleteGroupState = DeleteGroupState.Loading)) }
        viewModelScope.launch {
            try {
                deleteGroupsUseCase.execute(currentGroupState.selectedGroups.keys)
                _state.update {
                    it.copy(
                        dialogState = DialogState.NoDialog,
                        groupViewState = currentGroupState.copy(
                            deleteGroupState = DeleteGroupState.Success
                        )
                    )
                }
            } catch (_: Exception) {
                _state.update {
                    it.copy(groupViewState = currentGroupState.copy(deleteGroupState = DeleteGroupState.Error))
                }
            }
        }
    }

    private fun editGroup(groupName: String) {
        val currentGroupState = _state.value.groupViewState as? GroupViewState.Success
        if (currentGroupState == null) {
            _state.update { it.copy(groupViewState = GroupViewState.Error) }
            return
        }
        val selectedGroup = getSingleSelectedGroup()
        if (selectedGroup == null) {
            _state.update { it.copy(groupViewState = currentGroupState.copy(editGroupState = EditGroupState.Error)) }
            return
        }
        _state.update { it.copy(groupViewState = currentGroupState.copy(editGroupState = EditGroupState.Loading)) }
        viewModelScope.launch {
            try {
                editGroupUseCase.execute(selectedGroup.id, groupName)
                _state.update {
                    it.copy(
                        dialogState = DialogState.NoDialog,
                        groupViewState = currentGroupState.copy(
                            editGroupState = EditGroupState.Success
                        )
                    )
                }
            } catch (_: Exception) {
                _state.update {
                    it.copy(
                        groupViewState = currentGroupState.copy(
                            editGroupState = EditGroupState.Error
                        )
                    )
                }
            }
        }
    }

    private fun getSingleSelectedGroup(): GroupModel? {
        return (_state.value.groupViewState as? GroupViewState.Success)?.selectedGroups?.values?.firstOrNull()
    }

    private fun getSingleSelectedGroupName(): String {
        return getSingleSelectedGroup()?.name ?: ""
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
        val selectedGroups: Map<Long, GroupModel> = emptyMap(),
        val deleteGroupState: DeleteGroupState? = null,
        val editGroupState: EditGroupState? = null
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
    data class OnMarkerClick(val locationModel: LocationModel) : ViewEvent
}
