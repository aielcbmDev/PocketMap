package charly.baquero.pocketmap.di

import charly.baquero.pocketmap.ui.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get(), get(), get()) }
}
