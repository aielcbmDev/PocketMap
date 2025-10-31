package com.charly.database.utils

import androidx.room.exclusiveTransaction
import androidx.room.useWriterConnection
import com.charly.database.OpenClassForMocking
import com.charly.database.PocketMapDatabase
import com.charly.database.datasources.GroupDataSource
import com.charly.database.datasources.LocationDataSource
import com.charly.database.datasources.MembershipDataSource

@OpenClassForMocking
class PrePopulateTables(
    private val pocketMapDatabase: PocketMapDatabase,
    private val groupDataSource: GroupDataSource,
    private val locationDataSource: LocationDataSource,
    private val membershipDataSource: MembershipDataSource,
    private val assetFileProvider: AssetFileProvider
) {

    suspend fun execute() {
        pocketMapDatabase.useWriterConnection { transactor ->
            transactor.exclusiveTransaction {
                prePopulateLocations()
                prePopulateGroups()
                prePopulateMemberships()
            }
        }
    }

    private suspend fun prePopulateLocations() {
        val locationEntityList = assetFileProvider.getLocationEntityList()
        locationDataSource.insertOrReplaceListOfLocations(locationEntityList)
    }

    private suspend fun prePopulateGroups() {
        val groupEntityList = assetFileProvider.getGroupEntityList()
        groupDataSource.insertOrReplaceListOfGroups(groupEntityList)
    }

    private suspend fun prePopulateMemberships() {
        val membershipEntityList = assetFileProvider.getMembershipEntityList()
        membershipDataSource.insertOrReplaceListOfMemberships(membershipEntityList)
    }
}
