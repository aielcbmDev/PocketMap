package com.charly.database.utils

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.charly.database.PocketMapDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

fun RoomDatabase.Builder<PocketMapDatabase>.getRoomDatabase(): PocketMapDatabase {
    return addMigrations()
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
