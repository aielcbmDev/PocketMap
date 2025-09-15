package charly.baquero.pocketmap.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MapPane() {
    MapComponent()

    val viewModel = koinViewModel<MapViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    when (state) {
        is MapViewState.Loading -> {
            // unused for now
        }
        is MapViewState.Success -> {
            // unused for now
        }
    }
}
