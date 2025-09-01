package charly.baquero.pocketmap.data.database.model.groups

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GroupDao {

    @Query("SELECT * FROM groups_table;")
    suspend fun getAllGroups(): List<GroupEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertOrReplaceGroup(groupEntity: GroupEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertOrReplaceListOfGroups(groupEntityList: List<GroupEntity>)
}
