package charly.baquero.pocketmap.ui.map

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapViewModel : ViewModel() {

    private val _state = MutableStateFlow<MapViewState>(MapViewState.Loading)
    val state: StateFlow<MapViewState> = _state
}

sealed interface MapViewState {
    data object Loading : MapViewState
    data object Success : MapViewState
}
