package charly.baquero.pocketmap.di

import charly.baquero.pocketmap.domain.add.AddGroupUseCase
import charly.baquero.pocketmap.domain.get.GetAllGroupsUseCase
import charly.baquero.pocketmap.domain.get.GetAllLocationsForGroupUseCase
import charly.baquero.pocketmap.ui.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<GetAllGroupsUseCase> { GetAllGroupsUseCase(get()) }
    factory<GetAllLocationsForGroupUseCase> { GetAllLocationsForGroupUseCase(get()) }
    factory<AddGroupUseCase> { AddGroupUseCase(get()) }
    viewModel { MainViewModel(get(), get(), get()) }
}
