package com.charly.database.model.membership

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.charly.database.model.groups.GroupEntity
import com.charly.database.model.locations.LocationEntity

@Dao
interface MembershipDao {

    @Query("SELECT * FROM membership_table;")
    suspend fun getAllMemberships(): List<MembershipEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertOrReplaceMembership(membershipEntity: MembershipEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertOrReplaceListOfMemberships(membershipEntityList: List<MembershipEntity>)

    @Query("SELECT * FROM groups_table INNER JOIN membership_table ON groups_table.id=membership_table.idGroup WHERE membership_table.idLocation=:idLocation")
    suspend fun getGroupsForLocation(idLocation: Long): List<GroupEntity>

    @Query("SELECT * FROM locations_table INNER JOIN membership_table ON locations_table.id=membership_table.idLocation WHERE membership_table.idGroup=:idGroup")
    suspend fun getLocationsForGroup(idGroup: Long): List<LocationEntity>
}
