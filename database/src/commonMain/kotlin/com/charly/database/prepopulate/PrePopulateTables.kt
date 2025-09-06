package com.charly.database.prepopulate

import androidx.room.exclusiveTransaction
import androidx.room.useWriterConnection
import com.charly.OpenClassForMocking
import com.charly.database.PocketMapDatabase
import com.charly.database.model.groups.GroupDao
import com.charly.database.model.locations.LocationDao
import com.charly.database.model.membership.MembershipDao
import com.charly.utils.AssetFileProvider

@OpenClassForMocking
class PrePopulateTables(
    private val pocketMapDatabase: PocketMapDatabase,
    private val locationDao: LocationDao,
    private val groupDao: GroupDao,
    private val membershipDao: MembershipDao,
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
        locationDao.insertOrReplaceListOfLocations(locationEntityList)
    }

    private suspend fun prePopulateGroups() {
        val groupEntityList = assetFileProvider.getGroupEntityList()
        groupDao.insertOrReplaceListOfGroups(groupEntityList)
    }

    private suspend fun prePopulateMemberships() {
        val membershipEntityList = assetFileProvider.getMembershipEntityList()
        membershipDao.insertOrReplaceListOfMemberships(membershipEntityList)
    }
}
