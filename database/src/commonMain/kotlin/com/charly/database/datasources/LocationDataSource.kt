package com.charly.database.datasources

import com.charly.database.model.locations.LocationDao
import com.charly.database.model.locations.LocationEntity

class LocationDataSource(private val locationDao: LocationDao) {

    suspend fun getAllLocations(): List<LocationEntity> {
        return locationDao.getAllLocations()
    }

    suspend fun insertOrReplaceLocation(locationEntity: LocationEntity) {
        locationDao.insertOrReplaceLocation(locationEntity)
    }

    suspend fun insertOrReplaceListOfLocations(locationEntityList: List<LocationEntity>) {
        locationDao.insertOrReplaceListOfLocations(locationEntityList)
    }
}
