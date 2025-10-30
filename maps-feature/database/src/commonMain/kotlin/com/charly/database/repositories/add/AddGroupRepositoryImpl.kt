package com.charly.database.repositories.add

import com.charly.database.datasources.GroupDataSource
import com.charly.database.utils.mapToGroupEntity
import com.charly.domain.repositories.add.AddGroupRepository

class AddGroupRepositoryImpl(
    private val groupDataSource: GroupDataSource
) : AddGroupRepository {

    override suspend fun execute(groupName: String) {
        val groupEntity = groupName.mapToGroupEntity()
        groupDataSource.insertGroup(groupEntity)
    }
}
