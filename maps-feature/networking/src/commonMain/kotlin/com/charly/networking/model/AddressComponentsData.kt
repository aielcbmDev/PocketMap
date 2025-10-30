package com.charly.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressComponentsData(

    @SerialName("long_name")
    val longName: String?,

    @SerialName("short_name")
    val shortName: String?,

    @SerialName("types")
    val types: List<String>
)
