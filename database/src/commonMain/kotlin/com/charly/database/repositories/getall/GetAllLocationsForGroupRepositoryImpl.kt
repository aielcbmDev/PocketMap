package com.charly.database.repositories.getall

import com.charly.database.datasources.MembershipDataSource
import com.charly.database.utils.mapToLocationList
import com.charly.domain.model.Location
import com.charly.domain.repositories.get.GetAllLocationsForGroupRepository

class GetAllLocationsForGroupRepositoryImpl(
    private val membershipDataSource: MembershipDataSource
) : GetAllLocationsForGroupRepository {

    override suspend fun execute(idGroup: Long): List<Location> {
        return membershipDataSource.getLocationsForGroup(idGroup).mapToLocationList()
    }
}
