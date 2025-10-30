package com.charly.database.di

import androidx.room.RoomDatabase
import com.charly.database.PocketMapDatabase
import com.charly.database.createDatabaseBuilder
import com.charly.database.isDatabaseCreated
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal actual val databasePlatformModule: Module
    get() = module {
        factory<Boolean>(named("isDatabaseCreated")) {
            isDatabaseCreated()
        }
        factory<RoomDatabase.Builder<PocketMapDatabase>> {
            createDatabaseBuilder()
        }
    }
