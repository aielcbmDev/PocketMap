package charly.baquero.pocketmap.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import charly.baquero.pocketmap.data.database.model.groups.GroupDao
import charly.baquero.pocketmap.data.database.model.groups.GroupEntity
import charly.baquero.pocketmap.data.database.model.locations.LocationDao
import charly.baquero.pocketmap.data.database.model.locations.LocationEntity
import charly.baquero.pocketmap.data.database.model.membership.MembershipDao
import charly.baquero.pocketmap.data.database.model.membership.MembershipEntity

@Database(
    entities = [LocationEntity::class, GroupEntity::class, MembershipEntity::class],
    version = 1
)
@ConstructedBy(PocketMapDatabaseConstructor::class)
abstract class PocketMapDatabase : RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
    abstract fun getGroupDao(): GroupDao
    abstract fun getMembershipDao(): MembershipDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PocketMapDatabaseConstructor : RoomDatabaseConstructor<PocketMapDatabase> {
    override fun initialize(): PocketMapDatabase
}
