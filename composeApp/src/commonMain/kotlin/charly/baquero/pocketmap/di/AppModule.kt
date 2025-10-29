package charly.baquero.pocketmap.di

import charly.baquero.pocketmap.ui.MainViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal expect val appPlatformModule: Module

val appModule = module {
    includes(appPlatformModule)
    viewModel {
        MainViewModel(
            getAllGroupsUseCase = get(),
            getAllLocationsForGroupUseCase = get(),
            addGroupUseCase = get(),
            deleteGroupsUseCase = get(),
            editGroupUseCase = get()
        )
    }
}
