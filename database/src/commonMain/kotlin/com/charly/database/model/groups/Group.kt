package com.charly.database.model.groups

import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val id: Long,
    val name: String
)
