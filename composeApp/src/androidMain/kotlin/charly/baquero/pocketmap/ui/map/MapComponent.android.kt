package charly.baquero.pocketmap.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import charly.baquero.pocketmap.domain.model.Location
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
actual fun MapComponent(locationList: List<Location>?) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        val coordinates = LatLng(51.50512, -0.08633)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(coordinates, 10f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            locationList?.forEach {
                Marker(
                    state = rememberUpdatedMarkerState(
                        position = LatLng(
                            it.latitude.toDouble(),
                            it.longitude.toDouble()
                        )
                    ),
                    title = it.title,
                    snippet = it.description
                )
            }
        }
    }
}
