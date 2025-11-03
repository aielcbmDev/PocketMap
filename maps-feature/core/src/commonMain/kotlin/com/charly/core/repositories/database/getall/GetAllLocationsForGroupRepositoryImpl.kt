package com.charly.core.repositories.database.getall

import com.charly.core.mappers.database.mapToLocationList
import com.charly.database.datasources.MembershipDataSource
import com.charly.domain.model.database.Location
import com.charly.domain.repositories.database.get.GetAllLocationsForGroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllLocationsForGroupRepositoryImpl(
    private val membershipDataSource: MembershipDataSource
) : GetAllLocationsForGroupRepository {

    override suspend fun execute(idGroup: Long): Flow<List<Location>> {
        return membershipDataSource.getLocationsForGroup(idGroup).map { it.mapToLocationList() }
    }
}
