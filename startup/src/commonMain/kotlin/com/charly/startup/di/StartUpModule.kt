package com.charly.startup.di

import com.charly.startup.ui.StartUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val startUpModule = module {
    viewModel { StartUpViewModel(get()) }
}
