package charly.baquero.pocketmap.di

import charly.baquero.pocketmap.domain.GetAllGroupsUseCase
import charly.baquero.pocketmap.domain.GetAllLocationsForGroupUseCase
import charly.baquero.pocketmap.ui.display.DisplayDataViewModel
import charly.baquero.pocketmap.ui.map.MapViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MapViewModel() }
    factory<GetAllGroupsUseCase> { GetAllGroupsUseCase(get()) }
    factory<GetAllLocationsForGroupUseCase> { GetAllLocationsForGroupUseCase(get()) }
    viewModel { DisplayDataViewModel(get(), get()) }
}
