package com.charly.domain.di

import com.charly.domain.usecases.database.add.AddGroupUseCase
import com.charly.domain.usecases.database.delete.DeleteGroupsUseCase
import com.charly.domain.usecases.database.edit.EditGroupUseCase
import com.charly.domain.usecases.database.get.GetAllGroupsUseCase
import com.charly.domain.usecases.database.get.GetAllLocationsForGroupUseCase
import com.charly.domain.usecases.database.prepopulate.PrePopulateDatabaseUseCase
import com.charly.domain.usecases.networking.ReverseGeocodingUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetAllGroupsUseCase> { GetAllGroupsUseCase(get()) }
    factory<GetAllLocationsForGroupUseCase> { GetAllLocationsForGroupUseCase(get()) }
    factory<AddGroupUseCase> { AddGroupUseCase(get()) }
    factory<DeleteGroupsUseCase> { DeleteGroupsUseCase(get()) }
    factory<EditGroupUseCase> { EditGroupUseCase(get()) }
    factory<PrePopulateDatabaseUseCase> { PrePopulateDatabaseUseCase(get()) }
    factory<ReverseGeocodingUseCase> { ReverseGeocodingUseCase(get()) }
}
