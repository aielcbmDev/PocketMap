package charly.baquero.pocketmap.data.database

import androidx.room.RoomDatabase

interface DatabaseFactory {

    fun createDatabasePrePopulate(): Pair<RoomDatabase.Builder<PocketMapDatabase>, Boolean>

    fun createDatabase(): RoomDatabase.Builder<PocketMapDatabase>
}
