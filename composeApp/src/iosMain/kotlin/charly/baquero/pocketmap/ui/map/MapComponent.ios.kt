package charly.baquero.pocketmap.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import charly.baquero.pocketmap.mapViewController
import com.charly.domain.model.Location

@Composable
actual fun MapComponent(
    locationList: List<Location>?,
    locationSelected: Location?
) {
    UIKitViewController(
        factory = mapViewController,
        modifier = Modifier.fillMaxSize(),
    )
}
