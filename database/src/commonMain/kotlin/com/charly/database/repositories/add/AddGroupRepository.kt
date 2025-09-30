package com.charly.database.repositories.add

import com.charly.database.GroupDataSource
import com.charly.database.model.groups.GroupEntity

class AddGroupRepository(
    private val groupDataSource: GroupDataSource
) {

    suspend fun execute(groupEntity: GroupEntity) {
        groupDataSource.insertGroup(groupEntity)
    }
}
