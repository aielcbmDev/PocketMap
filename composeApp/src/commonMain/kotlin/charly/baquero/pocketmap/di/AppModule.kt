package charly.baquero.pocketmap.di

import charly.baquero.pocketmap.ui.map.MapViewModel
import charly.baquero.pocketmap.ui.startup.StartUpViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { StartUpViewModel(get()) }
    viewModel { MapViewModel() }
}

expect val platformModule: Module
