package charly.baquero.pocketmap.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val id: Long,
    val name: String
)