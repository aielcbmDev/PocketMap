package charly.baquero.pocketmap.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import charly.baquero.pocketmap.domain.model.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.launch

@Composable
actual fun MapComponent(
    locationList: List<Location>?,
    locationSelected: Location?
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        val coordinates = LatLng(51.50512, -0.08633)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(coordinates, 10f)
        }
        locationSelected?.let {
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(key1 = true) {
                coroutineScope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newCameraPosition(
                            CameraPosition(LatLng(it.latitude, it.longitude), 16f, 0f, 0f)
                        ),
                        durationMs = 2000
                    )
                }
            }
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = false,
                isTrafficEnabled = true,
            ),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                zoomGesturesEnabled = true,
                mapToolbarEnabled = true,
                scrollGesturesEnabled = true,
                rotationGesturesEnabled = true,
                tiltGesturesEnabled = false,
                scrollGesturesEnabledDuringRotateOrZoom = true,
            )
        ) {
            locationList?.forEach {
                Marker(
                    state = rememberUpdatedMarkerState(
                        position = LatLng(
                            it.latitude,
                            it.longitude
                        )
                    ),
                    title = it.title,
                    snippet = it.description
                )
            }
        }
    }
}
