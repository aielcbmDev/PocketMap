package com.charly.database.model.membership

import kotlinx.serialization.Serializable

@Serializable
data class Membership(
    val idGroup: Long,
    val idLocation: Long
)
