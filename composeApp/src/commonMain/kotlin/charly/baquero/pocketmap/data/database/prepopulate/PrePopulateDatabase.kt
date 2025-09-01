package charly.baquero.pocketmap.data.database.prepopulate

import androidx.room.RoomDatabase
import androidx.room.Transaction
import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
import charly.baquero.pocketmap.data.database.DatabaseFactory
import charly.baquero.pocketmap.data.database.PocketMapDatabase
import charly.baquero.pocketmap.data.database.model.groups.GroupEntity
import charly.baquero.pocketmap.data.database.model.locations.LocationEntity
import charly.baquero.pocketmap.data.database.model.membership.MembershipEntity
import charly.baquero.pocketmap.utils.AssetFileProvider
import charly.baquero.pocketmap.utils.getRoomDatabase

class PrePopulateDatabase(
    private val assetFileProvider: AssetFileProvider,
    private val databaseFactory: DatabaseFactory
) {

    private companion object {
        private const val LOCATIONS_FILE_PATH: String = "files/locations.json"
        private const val GROUPS_FILE_PATH: String = "files/groups.json"
        private const val MEMBERSHIPS_FILE_PATH: String = "files/memberships.json"
    }

    suspend fun execute() {
        val (roomDatabaseBuilder, exists) = databaseFactory.createDatabasePrePopulate()
        if (exists) return
        prePopulateTables(roomDatabaseBuilder)
    }

    @Transaction
    private suspend fun prePopulateTables(roomDatabaseBuilder: RoomDatabase.Builder<PocketMapDatabase>) {
        val pocketMapDatabase = roomDatabaseBuilder.getRoomDatabase()
        pocketMapDatabase.useWriterConnection { transactor ->
            transactor.immediateTransaction {
                prePopulateLocations(pocketMapDatabase)
                prePopulateGroups(pocketMapDatabase)
                prePopulateMemberships(pocketMapDatabase)
            }
        }
    }

    private suspend fun prePopulateLocations(pocketMapDatabase: PocketMapDatabase) {
        val locationEntityList = assetFileProvider.getDataList<LocationEntity>(LOCATIONS_FILE_PATH)
        pocketMapDatabase.getLocationDao().insertOrReplaceListOfLocations(locationEntityList)
    }

    private suspend fun prePopulateGroups(pocketMapDatabase: PocketMapDatabase) {
        val groupEntityList = assetFileProvider.getDataList<GroupEntity>(GROUPS_FILE_PATH)
        pocketMapDatabase.getGroupDao().insertOrReplaceListOfGroups(groupEntityList)
    }

    private suspend fun prePopulateMemberships(pocketMapDatabase: PocketMapDatabase) {
        val membershipEntityList =
            assetFileProvider.getDataList<MembershipEntity>(MEMBERSHIPS_FILE_PATH)
        pocketMapDatabase.getMembershipDao().insertOrReplaceListOfMemberships(membershipEntityList)
    }
}
