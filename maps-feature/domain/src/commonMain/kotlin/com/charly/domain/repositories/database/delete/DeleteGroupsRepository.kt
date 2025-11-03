package com.charly.domain.repositories.database.delete

interface DeleteGroupsRepository {

    suspend fun execute(groupIdsSelected: Set<Long>)
}
