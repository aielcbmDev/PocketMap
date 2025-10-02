package charly.baquero.pocketmap.ui.map

import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.ui.model.LocationModel

@Composable
expect fun MapComponent(
    locationList: List<LocationModel>? = null,
    locationSelected: LocationModel? = null
)
