package com.charly.database.repositories.getall

import com.charly.database.GroupDataSource
import com.charly.database.utils.mapToGroupList
import com.charly.domain.model.Group
import com.charly.domain.repositories.get.GetAllGroupsRepository

class GetAllGroupsRepositoryImpl(
    private val groupDataSource: GroupDataSource
) : GetAllGroupsRepository {

    override suspend fun execute(): List<Group> {
        return groupDataSource.getAllGroups().mapToGroupList()
    }
}
