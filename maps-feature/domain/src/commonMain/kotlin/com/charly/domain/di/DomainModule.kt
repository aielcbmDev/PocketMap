package com.charly.domain.di

import com.charly.domain.usecases.add.AddGroupUseCase
import com.charly.domain.usecases.delete.DeleteGroupsUseCase
import com.charly.domain.usecases.edit.EditGroupUseCase
import com.charly.domain.usecases.get.GetAllGroupsUseCase
import com.charly.domain.usecases.get.GetAllLocationsForGroupUseCase
import com.charly.domain.usecases.prepopulate.PrePopulateDatabaseUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetAllGroupsUseCase> { GetAllGroupsUseCase(get()) }
    factory<GetAllLocationsForGroupUseCase> { GetAllLocationsForGroupUseCase(get()) }
    factory<AddGroupUseCase> { AddGroupUseCase(get()) }
    factory<DeleteGroupsUseCase> { DeleteGroupsUseCase(get()) }
    factory<EditGroupUseCase> { EditGroupUseCase(get()) }
    factory<PrePopulateDatabaseUseCase> { PrePopulateDatabaseUseCase(get()) }
}
