package com.charly.database

import com.charly.database.model.groups.GroupDao
import com.charly.database.model.groups.GroupEntity

class GroupDataSource(private val groupDao: GroupDao) {

    suspend fun getAllGroups(): List<GroupEntity> {
        return groupDao.getAllGroups()
    }

    suspend fun insertOrReplaceGroup(groupEntity: GroupEntity) {
        groupDao.insertOrReplaceGroup(groupEntity)
    }

    suspend fun insertOrReplaceListOfGroups(groupEntityList: List<GroupEntity>) {
        groupDao.insertOrReplaceListOfGroups(groupEntityList)
    }
}
