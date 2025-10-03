package charly.baquero.pocketmap.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class GroupModel(
    val id: Long,
    val name: String,
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?) = other?.let { id == (it as GroupModel).id } ?: false
}
