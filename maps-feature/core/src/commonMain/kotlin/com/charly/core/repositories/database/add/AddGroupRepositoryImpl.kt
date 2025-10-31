package com.charly.core.repositories.database.add

import com.charly.core.mappers.mapToGroupEntity
import com.charly.database.datasources.GroupDataSource
import com.charly.domain.repositories.add.AddGroupRepository

class AddGroupRepositoryImpl(
    private val groupDataSource: GroupDataSource
) : AddGroupRepository {

    override suspend fun execute(groupName: String) {
        val groupEntity = groupName.mapToGroupEntity()
        groupDataSource.insertGroup(groupEntity)
    }
}
