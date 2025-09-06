package com.charly.database.di

import androidx.room.RoomDatabase
import com.charly.database.PocketMapDatabase
import com.charly.database.model.groups.GroupDao
import com.charly.database.model.locations.LocationDao
import com.charly.database.model.membership.MembershipDao
import com.charly.database.prepopulate.PrePopulateDatabase
import com.charly.database.prepopulate.PrePopulateTables
import com.charly.utils.AssetFileProvider
import com.charly.utils.getRoomDatabase
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val databasePlatformModule: Module

val databaseMainModule = module {
    factory<PocketMapDatabase> {
        get<RoomDatabase.Builder<PocketMapDatabase>>().getRoomDatabase()
    }

    single<LocationDao> {
        get<PocketMapDatabase>().getLocationDao()
    }

    single<GroupDao> {
        get<PocketMapDatabase>().getGroupDao()
    }

    single<MembershipDao> {
        get<PocketMapDatabase>().getMembershipDao()
    }

    single<AssetFileProvider> {
        AssetFileProvider()
    }

    single<PrePopulateTables> {
        PrePopulateTables(
            pocketMapDatabase = get(),
            locationDao = get(),
            groupDao = get(),
            membershipDao = get(),
            assetFileProvider = get()
        )
    }

    single<PrePopulateDatabase> {
        PrePopulateDatabase(
            get(named("isDatabaseCreated")),
            lazy { get() }
        )
    }
}
