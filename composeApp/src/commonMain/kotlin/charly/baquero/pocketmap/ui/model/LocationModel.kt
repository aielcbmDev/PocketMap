package charly.baquero.pocketmap.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationModel(
    val id: Long,
    val title: String,
    val description: String? = null,
    val latitude: Double,
    val longitude: Double,
)
