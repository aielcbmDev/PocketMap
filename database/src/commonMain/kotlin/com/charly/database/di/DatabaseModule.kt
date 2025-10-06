package com.charly.database.di

import androidx.room.RoomDatabase
import com.charly.database.PocketMapDatabase
import com.charly.database.datasources.GroupDataSource
import com.charly.database.datasources.LocationDataSource
import com.charly.database.datasources.MembershipDataSource
import com.charly.database.repositories.add.AddGroupRepositoryImpl
import com.charly.database.repositories.delete.DeleteGroupsRepositoryImpl
import com.charly.database.repositories.edit.EditGroupRepositoryImpl
import com.charly.database.repositories.getall.GetAllGroupsRepositoryImpl
import com.charly.database.repositories.getall.GetAllLocationsForGroupRepositoryImpl
import com.charly.database.repositories.prepopulate.PrePopulateDatabaseRepositoryImpl
import com.charly.database.repositories.prepopulate.PrePopulateTables
import com.charly.database.utils.AssetFileProvider
import com.charly.database.utils.getRoomDatabase
import com.charly.domain.repositories.add.AddGroupRepository
import com.charly.domain.repositories.delete.DeleteGroupsRepository
import com.charly.domain.repositories.edit.EditGroupRepository
import com.charly.domain.repositories.get.GetAllGroupsRepository
import com.charly.domain.repositories.get.GetAllLocationsForGroupRepository
import com.charly.domain.repositories.prepopulate.PrePopulateDatabaseRepository
import org.koin.core.module.Module
import org.koin.core.qualifier.named
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
        PrePopulateTables(
            pocketMapDatabase = get(),
            groupDataSource = get(),
            locationDataSource = get(),
            membershipDataSource = get(),
            assetFileProvider = get()
        )
    }

    factory<PrePopulateDatabaseRepository> {
        PrePopulateDatabaseRepositoryImpl(
            get(named("isDatabaseCreated")),
            lazy { get() }
        )
    }

    factory<GetAllGroupsRepository> {
        GetAllGroupsRepositoryImpl(get())
    }

    factory<GetAllLocationsForGroupRepository> {
        GetAllLocationsForGroupRepositoryImpl(get())
    }

    factory<AddGroupRepository> {
        AddGroupRepositoryImpl(get())
    }

    factory<DeleteGroupsRepository> {
        DeleteGroupsRepositoryImpl(get())
    }

    factory<EditGroupRepository> {
        EditGroupRepositoryImpl(get())
    }
}
