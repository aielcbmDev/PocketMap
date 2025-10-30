package com.charly.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ViewportData(

    @SerialName("northeast")
    val northeast: NortheastData?,

    @SerialName("southwest")
    val southwest: SouthwestData?
)
