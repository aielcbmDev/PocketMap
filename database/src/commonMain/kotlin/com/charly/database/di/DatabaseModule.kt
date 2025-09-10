package com.charly.database.di

import androidx.room.RoomDatabase
import com.charly.database.GroupDataSource
import com.charly.database.LocationDataSource
import com.charly.database.MembershipDataSource
import com.charly.database.PocketMapDatabase
import com.charly.database.model.groups.GroupDao
import com.charly.database.model.locations.LocationDao
import com.charly.database.model.membership.MembershipDao
import com.charly.database.prepopulate.PrePopulateDatabaseRepository
import com.charly.database.prepopulate.PrePopulateTables
import com.charly.database.utils.AssetFileProvider
import com.charly.database.utils.getRoomDatabase
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val databasePlatformModule: Module

val databaseMainModule = module {
    single<PocketMapDatabase> {
        get<RoomDatabase.Builder<PocketMapDatabase>>().getRoomDatabase()
    }

    single<LocationDao> {
        get<PocketMapDatabase>().getLocationDao()
    }

    single<LocationDataSource> {
        LocationDataSource(get())
    }

    single<GroupDao> {
        get<PocketMapDatabase>().getGroupDao()
    }

    single<GroupDataSource> {
        GroupDataSource(get())
    }

    single<MembershipDao> {
        get<PocketMapDatabase>().getMembershipDao()
    }

    single<MembershipDataSource> {
        MembershipDataSource(get())
    }

    factory<AssetFileProvider> {
        AssetFileProvider()
    }

    factory<PrePopulateTables> {
        PrePopulateTables(
            pocketMapDatabase = get(),
            groupDataSource = get(),
            locationDataSource = get(),
            membershipDataSource = get(),
            assetFileProvider = get()
        )
    }

    factory<PrePopulateDatabaseRepository> {
        PrePopulateDatabaseRepository(
            get(named("isDatabaseCreated")),
            lazy { get() }
        )
    }
}
