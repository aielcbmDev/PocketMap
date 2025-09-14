package charly.baquero.pocketmap.ui

import androidx.lifecycle.ViewModel
import charly.baquero.pocketmap.ui.navigation.BottomTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow<MainViewState>(MainViewState.MapTabSelected)
    val state: StateFlow<MainViewState> = _state

    fun setSelectedTab(bottomTab: BottomTab) {
        _state.value = when (bottomTab) {
            BottomTab.Map -> MainViewState.MapTabSelected
            BottomTab.Groups -> MainViewState.GroupsTabSelected
        }
    }
}

sealed interface MainViewState {
    data object MapTabSelected : MainViewState
    data object GroupsTabSelected : MainViewState
}
