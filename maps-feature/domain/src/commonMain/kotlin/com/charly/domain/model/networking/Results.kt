package com.charly.domain.model.networking

data class Results(
    val addressComponents: List<AddressComponents>?,
    val formattedAddress: String?,
    val geometry: Geometry?,
    val placeId: String?,
    val plusCode: PlusCode?,
    val types: List<String>
)
