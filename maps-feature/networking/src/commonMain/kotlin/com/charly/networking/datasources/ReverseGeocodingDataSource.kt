package com.charly.networking.datasources

import com.charly.networking.MapsApiService
import com.charly.networking.model.GeocodingData

class ReverseGeocodingDataSource(
    private val mapsApiService: MapsApiService
) {

    suspend fun execute(latitude: Double, longitude: Double): GeocodingData {
        return mapsApiService.getAddressForLocation(latitude, longitude)
    }
}
