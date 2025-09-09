package charly.baquero.pocketmap.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val _state = MutableStateFlow<MapViewState>(MapViewState.Loading)
    val state: StateFlow<MapViewState> = _state

    init {
        delayAndContinue()
    }

    private fun delayAndContinue() {
        _state.value = MapViewState.Loading
        viewModelScope.launch {
            delay(5000L)
            _state.value = MapViewState.Success
        }
    }
}

sealed interface MapViewState {
    data object Loading : MapViewState
    data object Success : MapViewState
}
