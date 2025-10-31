package com.charly.core.di

import com.charly.core.repositories.database.add.AddGroupRepositoryImpl
import com.charly.core.repositories.database.delete.DeleteGroupsRepositoryImpl
import com.charly.core.repositories.database.edit.EditGroupRepositoryImpl
import com.charly.core.repositories.database.getall.GetAllGroupsRepositoryImpl
import com.charly.core.repositories.database.getall.GetAllLocationsForGroupRepositoryImpl
import com.charly.core.repositories.database.prepopulate.PrePopulateDatabaseRepositoryImpl
import com.charly.database.di.databaseModule
import com.charly.domain.repositories.add.AddGroupRepository
import com.charly.domain.repositories.delete.DeleteGroupsRepository
import com.charly.domain.repositories.edit.EditGroupRepository
import com.charly.domain.repositories.get.GetAllGroupsRepository
import com.charly.domain.repositories.get.GetAllLocationsForGroupRepository
import com.charly.domain.repositories.prepopulate.PrePopulateDatabaseRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coreModule = module {
    includes(databaseModule)

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
