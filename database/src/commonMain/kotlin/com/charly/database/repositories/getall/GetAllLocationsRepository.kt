package com.charly.database.repositories.getall

import com.charly.database.LocationDataSource
import com.charly.database.model.locations.LocationEntity

class GetAllLocationsRepository(
    private val locationDataSource: LocationDataSource
) {

    suspend fun execute(): List<LocationEntity> {
        return locationDataSource.getAllLocations()
    }
}
