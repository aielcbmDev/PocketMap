package com.charly.database.model.locations

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: Long,
    val title: String,
    val description: String? = null,
    val latitude: Float,
    val longitude: Float,
)
