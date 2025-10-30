package com.charly.domain.repositories.get

import com.charly.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface GetAllLocationsForGroupRepository {

    suspend fun execute(idGroup: Long): Flow<List<Location>>
}
