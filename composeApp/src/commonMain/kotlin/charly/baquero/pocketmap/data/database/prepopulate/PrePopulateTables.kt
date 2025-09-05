package charly.baquero.pocketmap.data.database.prepopulate

import androidx.room.exclusiveTransaction
import androidx.room.useWriterConnection
import charly.baquero.pocketmap.OpenClassForMocking
import charly.baquero.pocketmap.data.database.PocketMapDatabase
import charly.baquero.pocketmap.data.database.model.groups.GroupDao
import charly.baquero.pocketmap.data.database.model.locations.LocationDao
import charly.baquero.pocketmap.data.database.model.membership.MembershipDao
import charly.baquero.pocketmap.utils.AssetFileProvider

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
