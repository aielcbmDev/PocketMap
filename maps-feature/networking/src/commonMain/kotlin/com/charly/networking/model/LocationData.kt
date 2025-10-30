package com.charly.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationData(

    @SerialName("lat")
    val lat: Double?,

    @SerialName("lng")
    val lng: Double?
)
