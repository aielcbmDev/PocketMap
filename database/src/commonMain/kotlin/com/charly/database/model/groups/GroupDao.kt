package com.charly.database.model.groups

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Query("SELECT * FROM groups_table ORDER BY name")
    fun getAllGroups(): Flow<List<GroupEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insertGroup(groupEntity: GroupEntity): Long

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertOrReplaceListOfGroups(groupEntityList: List<GroupEntity>)

    @Query("DELETE from groups_table where id in (:groupIdsSelected)")
    suspend fun deleteGroups(groupIdsSelected: Set<Long>)

    @Query("UPDATE groups_table SET name = :name WHERE id =:id")
    suspend fun updateGroupName(id: Long, name: String)
}
