package charly.baquero.pocketmap.di

import charly.baquero.pocketmap.domain.GetAllGroupsUseCase
import charly.baquero.pocketmap.domain.PrePopulateDatabaseUseCase
import charly.baquero.pocketmap.ui.display.DisplayGroupsViewModel
import charly.baquero.pocketmap.ui.map.MapViewModel
import charly.baquero.pocketmap.ui.startup.StartUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<PrePopulateDatabaseUseCase> { PrePopulateDatabaseUseCase(get()) }
    viewModel { StartUpViewModel(get()) }
    viewModel { MapViewModel() }
    factory<GetAllGroupsUseCase> { GetAllGroupsUseCase(get()) }
    viewModel { DisplayGroupsViewModel(get()) }
}
