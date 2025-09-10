package com.charly.database.repositories.getall

import com.charly.database.LocationDataSource
import com.charly.database.model.locations.Location
import com.charly.database.utils.mapToLocationList

class GetAllLocationsRepository(
    private val locationDataSource: LocationDataSource
) {

    suspend fun execute(): List<Location> {
        return locationDataSource.getAllLocations().mapToLocationList()
    }
}
