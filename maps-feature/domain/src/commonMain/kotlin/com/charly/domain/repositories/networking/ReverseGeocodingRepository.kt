package com.charly.domain.repositories.networking

import com.charly.domain.model.networking.Geocoding

fun interface ReverseGeocodingRepository {

    suspend fun execute(latitude: Double, longitude: Double): Geocoding
}
