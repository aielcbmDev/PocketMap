package com.charly.domain.usecases.networking

import com.charly.domain.model.networking.Geocoding
import com.charly.domain.repositories.networking.ReverseGeocodingRepository

class ReverseGeocodingUseCase(
    private val reverseGeocodingRepository: ReverseGeocodingRepository
) {

    suspend fun execute(latitude: Double, longitude: Double): Geocoding {
        return reverseGeocodingRepository.execute(latitude, longitude)
    }
}
