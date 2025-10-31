package com.charly.core.repositories.database.getall

import com.charly.core.mappers.mapToGroupList
import com.charly.database.datasources.GroupDataSource
import com.charly.domain.model.Group
import com.charly.domain.repositories.get.GetAllGroupsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllGroupsRepositoryImpl(
    private val groupDataSource: GroupDataSource
) : GetAllGroupsRepository {

    override suspend fun execute(): Flow<List<Group>> {
        return groupDataSource.getAllGroups().map { it.mapToGroupList() }
    }
}
