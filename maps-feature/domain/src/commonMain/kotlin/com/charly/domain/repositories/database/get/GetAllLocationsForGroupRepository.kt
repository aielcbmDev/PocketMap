package com.charly.domain.repositories.database.get

import com.charly.domain.model.database.Location
import kotlinx.coroutines.flow.Flow

interface GetAllLocationsForGroupRepository {

    suspend fun execute(idGroup: Long): Flow<List<Location>>
}
