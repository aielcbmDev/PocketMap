package com.charly.domain.repositories.delete

interface DeleteGroupsRepository {

    suspend fun execute(groupIdsSelected: Set<Long>)
}
