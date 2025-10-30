package com.charly.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeometryData(

    @SerialName("location")
    val location: LocationData?,

    @SerialName("location_type")
    val locationType: String?,

    @SerialName("viewport")
    val viewport: ViewportData?
)
