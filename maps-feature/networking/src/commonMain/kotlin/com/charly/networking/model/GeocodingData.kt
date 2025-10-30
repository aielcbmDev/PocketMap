package com.charly.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingData(

    @SerialName("results")
    val results: List<ResultsData>,

    @SerialName("status")
    val status: String?
)
