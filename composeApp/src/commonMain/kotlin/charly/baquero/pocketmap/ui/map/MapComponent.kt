package charly.baquero.pocketmap.ui.map

import androidx.compose.runtime.Composable
import com.charly.domain.model.Location

@Composable
expect fun MapComponent(
    locationList: List<Location>? = null,
    locationSelected: Location? = null
)
