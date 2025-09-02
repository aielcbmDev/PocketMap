package charly.baquero.pocketmap.ui.map

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MapScreen() {
    val viewModel = koinViewModel<MapViewModel>()
    MapComponent()
}
