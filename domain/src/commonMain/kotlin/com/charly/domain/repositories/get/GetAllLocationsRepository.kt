package com.charly.domain.repositories.get

import com.charly.domain.model.Location

interface GetAllLocationsRepository {

    suspend fun execute(): List<Location>
}
