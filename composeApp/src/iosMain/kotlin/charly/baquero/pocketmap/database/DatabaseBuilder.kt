package charly.baquero.pocketmap.database

import androidx.room.Room
import androidx.room.RoomDatabase
import charly.baquero.pocketmap.data.database.PocketMapDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

private const val DATABASE_NAME = "/pocket_map_ios.db"

@OptIn(ExperimentalForeignApi::class)
fun isDatabaseCreated(): Boolean {
    val nsFileManager = NSFileManager.Companion.defaultManager
    val dbFilePath = documentDirectory(nsFileManager) + DATABASE_NAME
    return nsFileManager.fileExistsAtPath(dbFilePath)
}

@OptIn(ExperimentalForeignApi::class)
fun createDatabaseBuilder(): RoomDatabase.Builder<PocketMapDatabase> {
    val nsFileManager = NSFileManager.Companion.defaultManager
    val dbFilePath = documentDirectory(nsFileManager) + DATABASE_NAME
    return Room.databaseBuilder<PocketMapDatabase>(
        name = dbFilePath
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(nsFileManager: NSFileManager): String {
    val documentDirectory = nsFileManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
