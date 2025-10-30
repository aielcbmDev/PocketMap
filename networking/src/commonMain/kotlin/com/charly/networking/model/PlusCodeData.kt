package com.charly.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlusCodeData(

    @SerialName("compound_code")
    val compoundCode: String?,

    @SerialName("global_code")
    val globalCode: String?
)
