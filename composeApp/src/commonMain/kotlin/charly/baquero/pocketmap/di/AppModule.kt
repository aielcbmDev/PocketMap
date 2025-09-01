package charly.baquero.pocketmap.di

import charly.baquero.pocketmap.data.database.prepopulate.PrePopulateDatabase
import charly.baquero.pocketmap.ui.map.MainScreenViewModel
import charly.baquero.pocketmap.ui.startup.StartUpViewModel
import charly.baquero.pocketmap.utils.AssetFileProvider
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AssetFileProvider> { AssetFileProvider() }
    single<PrePopulateDatabase> {
        PrePopulateDatabase(
            get(),
            get()
        )
    }
    viewModel { StartUpViewModel(get()) }
    viewModel { MainScreenViewModel() }
}

expect val platformModule: Module
