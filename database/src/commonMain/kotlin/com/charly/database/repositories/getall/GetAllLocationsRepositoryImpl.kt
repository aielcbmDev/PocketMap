package com.charly.database.repositories.getall

import com.charly.database.LocationDataSource
import com.charly.database.utils.mapToLocationList
import com.charly.domain.model.Location
import com.charly.domain.repositories.get.GetAllLocationsRepository

class GetAllLocationsRepositoryImpl(
    private val locationDataSource: LocationDataSource
) : GetAllLocationsRepository {

    override suspend fun execute(): List<Location> {
        return locationDataSource.getAllLocations().mapToLocationList()
    }
}
