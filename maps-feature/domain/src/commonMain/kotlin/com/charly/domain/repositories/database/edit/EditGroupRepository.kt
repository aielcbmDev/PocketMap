package com.charly.domain.repositories.database.edit

interface EditGroupRepository {

    suspend fun execute(id: Long, groupName: String)
}
