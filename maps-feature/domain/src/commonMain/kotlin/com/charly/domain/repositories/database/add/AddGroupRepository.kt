package com.charly.domain.repositories.database.add

interface AddGroupRepository {
    suspend fun execute(groupName: String)
}
