package charly.baquero.pocketmap.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import charly.baquero.pocketmap.mapViewController
import charly.baquero.pocketmap.ui.model.LocationModel
import kotlinx.serialization.json.Json

@Composable
actual fun MapComponent(
    locationList: List<LocationModel>?,
    locationSelected: LocationModel?
) {
    val locationsJson = locationList.getLocationsAsJson()
    key(locationsJson) {
        UIKitViewController(
            factory = {
                mapViewController(locationsJson)
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

private fun List<LocationModel>?.getLocationsAsJson(): String {
    return if (this == null) "[]" else Json.encodeToString(this)
}
