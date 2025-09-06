package com.charly.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.charly.database.model.groups.GroupDao
import com.charly.database.model.groups.GroupEntity
import com.charly.database.model.locations.LocationDao
import com.charly.database.model.locations.LocationEntity
import com.charly.database.model.membership.MembershipDao
import com.charly.database.model.membership.MembershipEntity

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
