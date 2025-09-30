package com.charly.database.model.groups

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GroupDao {

    @Query("SELECT * FROM groups_table ORDER BY name")
    suspend fun getAllGroups(): List<GroupEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insertGroup(groupEntity: GroupEntity): Long

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertOrReplaceListOfGroups(groupEntityList: List<GroupEntity>)
}
