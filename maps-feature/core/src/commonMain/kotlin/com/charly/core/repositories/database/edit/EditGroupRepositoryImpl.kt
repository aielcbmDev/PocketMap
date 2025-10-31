package com.charly.core.repositories.database.edit

import com.charly.database.datasources.GroupDataSource
import com.charly.domain.repositories.edit.EditGroupRepository

class EditGroupRepositoryImpl(
    private val groupDataSource: GroupDataSource
) : EditGroupRepository {

    override suspend fun execute(id: Long, groupName: String) {
        groupDataSource.updateGroupName(id, groupName)
    }
}
