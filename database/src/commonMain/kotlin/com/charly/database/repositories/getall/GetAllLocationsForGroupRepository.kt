package com.charly.database.repositories.getall

import com.charly.database.MembershipDataSource
import com.charly.database.model.locations.Location
import com.charly.database.utils.mapToLocationList

class GetAllLocationsForGroupRepository(
    private val membershipDataSource: MembershipDataSource
) {

    suspend fun execute(idGroup: Long): List<Location> {
        return membershipDataSource.getLocationsForGroup(idGroup).mapToLocationList()
    }
}
