package charly.baquero.pocketmap.di

import androidx.room.RoomDatabase
import charly.baquero.pocketmap.data.database.PocketMapDatabase
import charly.baquero.pocketmap.database.createDatabaseBuilder
import charly.baquero.pocketmap.database.isDatabaseCreated
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        factory<Boolean>(named("isDatabaseCreated")) {
            isDatabaseCreated(context = get())
        }
        factory<RoomDatabase.Builder<PocketMapDatabase>> {
            createDatabaseBuilder(context = get())
        }
    }
