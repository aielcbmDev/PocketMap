package charly.baquero.pocketmap.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import charly.baquero.pocketmap.data.database.DatabaseFactory
import charly.baquero.pocketmap.data.database.PocketMapDatabase

class AndroidDatabaseFactory(private val context: Context) : DatabaseFactory {

    private companion object {
        private const val DATABASE_NAME = "pocket_map_android.db"
    }

    override fun createDatabasePrePopulate(): Pair<RoomDatabase.Builder<PocketMapDatabase>, Boolean> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(DATABASE_NAME)
        return Pair(
            Room.databaseBuilder<PocketMapDatabase>(
                context = appContext,
                name = dbFile.absolutePath
            ),
            dbFile.exists()
        )
    }

    override fun createDatabase(): RoomDatabase.Builder<PocketMapDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(DATABASE_NAME)
        return Room.databaseBuilder<PocketMapDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}
