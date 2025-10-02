package charly.baquero.pocketmap.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import charly.baquero.pocketmap.mapViewController
import charly.baquero.pocketmap.ui.model.LocationModel

@Composable
actual fun MapComponent(
    locationList: List<LocationModel>?,
    locationSelected: LocationModel?
) {
    UIKitViewController(
        factory = mapViewController,
        modifier = Modifier.fillMaxSize(),
    )
}
