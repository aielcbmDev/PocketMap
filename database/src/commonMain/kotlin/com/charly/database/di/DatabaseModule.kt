package com.charly.database.di

import androidx.room.RoomDatabase
import com.charly.database.GroupDataSource
import com.charly.database.LocationDataSource
import com.charly.database.MembershipDataSource
import com.charly.database.PocketMapDatabase
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

    single<LocationDataSource> {
        val locationDao = get<PocketMapDatabase>().getLocationDao()
        LocationDataSource(locationDao)
    }

    single<GroupDataSource> {
        val groupDao = get<PocketMapDatabase>().getGroupDao()
        GroupDataSource(groupDao)
    }

    single<MembershipDataSource> {
        val membershipDao = get<PocketMapDatabase>().getMembershipDao()
        MembershipDataSource(membershipDao)
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
