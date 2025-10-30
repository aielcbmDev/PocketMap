package com.charly.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE_NAME = "pocket_map_android.db"

fun isDatabaseCreated(
    context: Context
): Boolean {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)
    return dbFile.exists()
}

fun createDatabaseBuilder(
    context: Context
): RoomDatabase.Builder<PocketMapDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<PocketMapDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
