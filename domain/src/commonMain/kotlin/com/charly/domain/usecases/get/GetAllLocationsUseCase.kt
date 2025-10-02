package com.charly.domain.usecases.get

import com.charly.domain.model.Location
import com.charly.domain.repositories.get.GetAllLocationsRepository

class GetAllLocationsUseCase(
    private val getAllLocationsRepository: GetAllLocationsRepository
) {

    suspend fun execute(): List<Location> {
        return getAllLocationsRepository.execute()
    }
}
