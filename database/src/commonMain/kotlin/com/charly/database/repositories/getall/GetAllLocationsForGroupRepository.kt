package com.charly.database.repositories.getall

import com.charly.database.MembershipDataSource
import com.charly.database.model.locations.LocationEntity

class GetAllLocationsForGroupRepository(
    private val membershipDataSource: MembershipDataSource
) {

    suspend fun execute(idGroup: Long): List<LocationEntity> {
        return membershipDataSource.getLocationsForGroup(idGroup)
    }
}
