package com.charly.domain.repositories.add

interface AddGroupRepository {
    suspend fun execute(groupName: String)
}
