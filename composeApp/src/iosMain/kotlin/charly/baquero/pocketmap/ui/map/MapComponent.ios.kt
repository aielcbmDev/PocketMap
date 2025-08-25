package charly.baquero.pocketmap.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import charly.baquero.pocketmap.mapViewController
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapComponent() {
    UIKitViewController(
        factory = mapViewController,
        modifier = Modifier.fillMaxSize(),
    )
}
