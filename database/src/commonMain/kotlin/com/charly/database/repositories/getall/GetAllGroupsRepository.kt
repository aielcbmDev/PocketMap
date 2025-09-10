package com.charly.database.repositories.getall

import com.charly.database.GroupDataSource
import com.charly.database.model.groups.Group
import com.charly.database.utils.mapToGroupList

class GetAllGroupsRepository(
    private val groupDataSource: GroupDataSource
) {

    suspend fun execute(): List<Group> {
        return groupDataSource.getAllGroups().mapToGroupList()
    }
}
