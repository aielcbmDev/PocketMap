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
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.currentCameraPositionState
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
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(LatLng(51.50512, -0.08633), 10f)
            },
            properties = MapProperties(
                isMyLocationEnabled = false,
                isTrafficEnabled = true,
            )
        ) {
            DisplayMarkers(
                locationList = locationList,
                locationSelected = locationSelected
            )
            MoveCameraToSelectedLocation(
                cameraPositionState = currentCameraPositionState,
                locationSelected = locationSelected
            )
        }
    }
}

@Composable
fun DisplayMarkers(
    locationList: List<Location>?,
    locationSelected: Location?
) {
    locationList?.forEach {
        val markerState = rememberUpdatedMarkerState(
            position = LatLng(it.latitude, it.longitude)
        )
        Marker(
            state = markerState,
            title = it.title,
            snippet = it.description
        )
        if (locationSelected?.id == it.id) {
            markerState.showInfoWindow()
        }
    }
}

@Composable
fun MoveCameraToSelectedLocation(
    cameraPositionState: CameraPositionState,
    locationSelected: Location?
) {
    locationSelected?.let {
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(key1 = locationSelected.id) {
            coroutineScope.launch {
                cameraPositionState.move(
                    update = CameraUpdateFactory.newCameraPosition(
                        CameraPosition.fromLatLngZoom(
                            LatLng(it.latitude, it.longitude),
                            14f
                        )
                    )
                )
            }
        }
    }
}
