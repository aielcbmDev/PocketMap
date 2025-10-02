package com.charly.domain.usecases.get

import com.charly.domain.model.Location
import com.charly.domain.repositories.get.GetAllLocationsForGroupRepository

class GetAllLocationsForGroupUseCase(
    private val getAllLocationsForGroupRepository: GetAllLocationsForGroupRepository
) {

    suspend fun execute(idGroup: Long): List<Location> {
        return getAllLocationsForGroupRepository.execute(idGroup)
    }
}
