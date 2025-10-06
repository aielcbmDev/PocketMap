package com.charly.domain.repositories.edit

interface EditGroupRepository {

    suspend fun execute(id: Long, groupName: String)
}
