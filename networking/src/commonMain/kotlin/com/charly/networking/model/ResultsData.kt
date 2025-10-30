package com.charly.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultsData(

    @SerialName("address_components")
    val addressComponents: List<AddressComponentsData>,

    @SerialName("formatted_address")
    val formattedAddress: String?,

    @SerialName("geometry")
    val geometry: GeometryData?,

    @SerialName("place_id")
    val placeId: String?,

    @SerialName("plus_code")
    val plusCode: PlusCodeData?,

    @SerialName("types")
    val types: List<String>
)
