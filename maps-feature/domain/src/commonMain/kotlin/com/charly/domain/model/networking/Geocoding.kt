package com.charly.domain.model.networking

data class Geocoding(
    val results: List<Results>?,
    val status: String?
)
