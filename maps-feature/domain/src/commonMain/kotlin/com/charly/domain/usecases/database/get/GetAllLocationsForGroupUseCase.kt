package com.charly.domain.usecases.database.get

import com.charly.domain.OpenClassForMocking
import com.charly.domain.model.database.Location
import com.charly.domain.repositories.database.get.GetAllLocationsForGroupRepository
import kotlinx.coroutines.flow.Flow

@OpenClassForMocking
class GetAllLocationsForGroupUseCase(
    private val getAllLocationsForGroupRepository: GetAllLocationsForGroupRepository
) {

    suspend fun execute(idGroup: Long): Flow<List<Location>> {
        return getAllLocationsForGroupRepository.execute(idGroup)
    }
}
