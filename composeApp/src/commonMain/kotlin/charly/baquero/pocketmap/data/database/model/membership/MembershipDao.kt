package charly.baquero.pocketmap.data.database.model.membership

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MembershipDao {

    @Query("SELECT * FROM membership_table;")
    suspend fun getAllMemberships(): List<MembershipEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertOrReplaceMembership(membershipEntity: MembershipEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertOrReplaceListOfMemberships(membershipEntityList: List<MembershipEntity>)
}
