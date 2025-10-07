package com.charly.database.datasources

import com.charly.database.model.groups.GroupDao
import com.charly.database.model.groups.GroupEntity
import kotlinx.coroutines.flow.Flow

class GroupDataSource(private val groupDao: GroupDao) {

    fun getAllGroups(): Flow<List<GroupEntity>> {
        return groupDao.getAllGroups()
    }

    suspend fun insertGroup(groupEntity: GroupEntity) {
        val result = groupDao.insertGroup(groupEntity)
        if (result == -1L) {
            throw Exception("Group already exists")
        }
    }

    suspend fun insertOrReplaceListOfGroups(groupEntityList: List<GroupEntity>) {
        groupDao.insertOrReplaceListOfGroups(groupEntityList)
    }

    suspend fun deleteGroups(groupIdsSelected: Set<Long>) {
        groupDao.deleteGroups(groupIdsSelected)
    }

    suspend fun updateGroupName(id: Long, name: String) {
        groupDao.updateGroupName(id, name)
    }
}
