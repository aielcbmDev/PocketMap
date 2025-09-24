package charly.baquero.pocketmap.ui.map

import androidx.compose.runtime.Composable
import charly.baquero.pocketmap.domain.model.Location

@Composable
expect fun MapComponent(
    locationList: List<Location>? = null,
    locationSelected: Location? = null
)
