package com.charly.core.repositories.database.delete

import com.charly.database.datasources.GroupDataSource
import com.charly.domain.repositories.database.delete.DeleteGroupsRepository

class DeleteGroupsRepositoryImpl(
    private val groupDataSource: GroupDataSource
) : DeleteGroupsRepository {

    override suspend fun execute(groupIdsSelected: Set<Long>) {
        groupDataSource.deleteGroups(groupIdsSelected)
    }
}
