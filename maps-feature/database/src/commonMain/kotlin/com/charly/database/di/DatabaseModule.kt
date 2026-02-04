package com.charly.database.di

import androidx.room.RoomDatabase
import com.charly.database.PocketMapDatabase
import com.charly.database.datasources.GroupDataSource
import com.charly.database.datasources.LocationDataSource
import com.charly.database.datasources.MembershipDataSource
import com.charly.database.utils.AssetFileProvider
import com.charly.database.utils.PrePopulateTables
import com.charly.database.utils.PrePopulateTablesImpl
import com.charly.database.utils.getRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect val databasePlatformModule: Module

val databaseModule = module {
    includes(databasePlatformModule)

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
        PrePopulateTablesImpl(
            pocketMapDatabase = get(),
            groupDataSource = get(),
            locationDataSource = get(),
            membershipDataSource = get(),
            assetFileProvider = get()
        )
    }
}
