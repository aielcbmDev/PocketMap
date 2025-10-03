package charly.baquero.pocketmap.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class GroupModel(
    val id: Long,
    val name: String,
)
