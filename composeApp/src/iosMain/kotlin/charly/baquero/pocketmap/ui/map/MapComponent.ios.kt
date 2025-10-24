package charly.baquero.pocketmap.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import charly.baquero.pocketmap.mapViewController
import charly.baquero.pocketmap.ui.model.LocationModel

@Composable
actual fun MapComponent(
    locationList: List<LocationModel>?,
    locationSelected: LocationModel?
) {
    key(locationList, locationSelected) {
        UIKitViewController(
            factory = {
                mapViewController(locationList, locationSelected)
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}
