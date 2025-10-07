package com.charly.database.datasources

import com.charly.database.model.groups.GroupEntity
import com.charly.database.model.locations.LocationEntity
import com.charly.database.model.membership.MembershipDao
import com.charly.database.model.membership.MembershipEntity
import kotlinx.coroutines.flow.Flow

class MembershipDataSource(private val membershipDao: MembershipDao) {

    suspend fun insertOrReplaceListOfMemberships(membershipEntityList: List<MembershipEntity>) {
        membershipDao.insertOrReplaceListOfMemberships(membershipEntityList)
    }

    suspend fun getGroupsForLocation(idLocation: Long): List<GroupEntity> {
        return membershipDao.getGroupsForLocation(idLocation)
    }

    fun getLocationsForGroup(idGroup: Long): Flow<List<LocationEntity>> {
        return membershipDao.getLocationsForGroup(idGroup)
    }
}
