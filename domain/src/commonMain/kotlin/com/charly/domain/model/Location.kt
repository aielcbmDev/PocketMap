package com.charly.domain.model

data class Location(
    val id: Long,
    val title: String,
    val description: String? = null,
    val latitude: Double,
    val longitude: Double,
)
