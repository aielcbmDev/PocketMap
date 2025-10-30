package com.charly.networking

import com.charly.networking.httpclient.HttpClientFactory
import com.charly.networking.model.GeocodingData
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * https://developers.google.com/maps/documentation/geocoding/requests-reverse-geocoding
 */
class MapsApiService(
    private val mapsApiKey: String,
    httpClientFactory: HttpClientFactory
) {

    private val httpClient = httpClientFactory.createHttpClient()

    suspend fun getAddressForLocation(
        latitude: Double,
        longitude: Double
    ): GeocodingData {
        val url = generateUrl(latitude, longitude, mapsApiKey)
        return httpClient.get(url).body<GeocodingData>()
    }

    private companion object Companion {

        private const val GEOCODING_BASE_URL =
            "https://maps.googleapis.com/maps/api/geocode/json?"

        fun generateUrl(latitude: Double, longitude: Double, mapsApiKey: String): String {
            return "${GEOCODING_BASE_URL}latlng=$latitude,$longitude&location_type=ROOFTOP&result_type=street_address&key=$mapsApiKey"
        }
    }
}
