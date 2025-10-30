package com.charly.domain.usecases.get

import com.charly.domain.OpenClassForMocking
import com.charly.domain.model.Location
import com.charly.domain.repositories.get.GetAllLocationsForGroupRepository
import kotlinx.coroutines.flow.Flow

@OpenClassForMocking
class GetAllLocationsForGroupUseCase(
    private val getAllLocationsForGroupRepository: GetAllLocationsForGroupRepository
) {

    suspend fun execute(idGroup: Long): Flow<List<Location>> {
        return getAllLocationsForGroupRepository.execute(idGroup)
    }
}
