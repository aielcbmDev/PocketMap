package com.charly.database.repositories.delete

import com.charly.database.datasources.GroupDataSource
import com.charly.domain.repositories.delete.DeleteGroupsRepository

class DeleteGroupsRepositoryImpl(
    private val groupDataSource: GroupDataSource
) : DeleteGroupsRepository {

    override suspend fun execute(groupIdsSelected: Set<Long>) {
        groupDataSource.deleteGroups(groupIdsSelected)
    }
}
