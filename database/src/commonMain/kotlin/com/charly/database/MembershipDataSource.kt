package com.charly.database

import com.charly.database.model.groups.GroupEntity
import com.charly.database.model.locations.LocationEntity
import com.charly.database.model.membership.MembershipDao
import com.charly.database.model.membership.MembershipEntity

class MembershipDataSource(private val membershipDao: MembershipDao) {

    suspend fun insertOrReplaceListOfMemberships(membershipEntityList: List<MembershipEntity>) {
        membershipDao.insertOrReplaceListOfMemberships(membershipEntityList)
    }

    suspend fun getGroupsForLocation(idLocation: Long): List<GroupEntity> {
        return membershipDao.getGroupsForLocation(idLocation)
    }

    suspend fun getLocationsForGroup(idGroup: Long): List<LocationEntity> {
        return membershipDao.getLocationsForGroup(idGroup)
    }
}
