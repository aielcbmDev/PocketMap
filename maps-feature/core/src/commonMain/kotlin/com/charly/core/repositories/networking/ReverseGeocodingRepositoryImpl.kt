package com.charly.core.repositories.networking

import com.charly.networking.datasources.ReverseGeocodingDataSource
import com.charly.networking.model.GeocodingData

class ReverseGeocodingRepositoryImpl(
    private val reverseGeocodingDataSource: ReverseGeocodingDataSource
) {

    suspend fun getReverseGeocoding(latitude: Double, longitude: Double): GeocodingData {
        return reverseGeocodingDataSource.getReverseGeocoding(latitude, longitude)
    }
}
