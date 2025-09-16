package charly.baquero.pocketmap.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: Long,
    val title: String,
    val description: String? = null,
    val latitude: Float,
    val longitude: Float,
)