package com.charly.core.repositories.networking

import com.charly.core.mappers.networking.mapToGeocoding
import com.charly.domain.model.networking.Geocoding
import com.charly.domain.repositories.networking.ReverseGeocodingRepository
import com.charly.networking.datasources.ReverseGeocodingDataSource

class ReverseGeocodingRepositoryImpl(
    private val reverseGeocodingDataSource: ReverseGeocodingDataSource
) : ReverseGeocodingRepository {

    override suspend fun execute(latitude: Double, longitude: Double): Geocoding {
        return reverseGeocodingDataSource.execute(latitude, longitude).mapToGeocoding()
    }
}
