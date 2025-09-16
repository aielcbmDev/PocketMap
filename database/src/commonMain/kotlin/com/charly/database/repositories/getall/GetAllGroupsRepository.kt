package com.charly.database.repositories.getall

import com.charly.database.GroupDataSource
import com.charly.database.model.groups.GroupEntity

class GetAllGroupsRepository(
    private val groupDataSource: GroupDataSource
) {

    suspend fun execute(): List<GroupEntity> {
        return groupDataSource.getAllGroups()
    }
}
