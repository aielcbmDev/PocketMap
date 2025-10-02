package com.charly.domain.repositories.get

import com.charly.domain.model.Location

interface GetAllLocationsForGroupRepository {

    suspend fun execute(idGroup: Long): List<Location>
}
