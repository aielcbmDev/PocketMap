package charly.baquero.pocketmap.database

import androidx.room.Room
import androidx.room.RoomDatabase
import charly.baquero.pocketmap.data.database.DatabaseFactory
import charly.baquero.pocketmap.data.database.PocketMapDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

class IOSDatabaseFactory : DatabaseFactory {

    private companion object {
        private const val DATABASE_NAME = "/pocket_map_ios.db"
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun createDatabasePrePopulate(): Pair<RoomDatabase.Builder<PocketMapDatabase>, Boolean> {
        val nsFileManager = NSFileManager.Companion.defaultManager
        val dbFilePath = documentDirectory(nsFileManager) + DATABASE_NAME
        val exists = nsFileManager.fileExistsAtPath(dbFilePath)
        return Pair(
            Room.databaseBuilder<PocketMapDatabase>(
                name = dbFilePath
            ),
            exists
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun createDatabase(): RoomDatabase.Builder<PocketMapDatabase> {
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
}
