package charly.baquero.pocketmap.ui.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import charly.baquero.pocketmap.domain.GetAllGroupsUseCase
import com.charly.database.model.groups.Group
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DisplayGroupsViewModel(
    private val getAllGroupsUseCase: GetAllGroupsUseCase
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
                _state.value = DisplayGroupsViewState.Success(groupList)
            } catch (_: Exception) {
                _state.value = DisplayGroupsViewState.Error
            }
        }
    }
}

sealed interface DisplayGroupsViewState {
    data object Loading : DisplayGroupsViewState
    data class Success(
        val groupList: List<Group>
    ) : DisplayGroupsViewState

    data object Error : DisplayGroupsViewState
}
