package charly.baquero.pocketmap.di

import charly.baquero.pocketmap.AppSecrets
import charly.baquero.pocketmap.ui.MainViewModel
import com.charly.qualifiers.MAPS_API_KEY_DI_NAME
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal expect val appPlatformModule: Module

val appModule = module {
    includes(appPlatformModule)
    single(named(MAPS_API_KEY_DI_NAME)) {
        get<AppSecrets>().getMapsApiKey()
    }
    viewModel {
        MainViewModel(
            getAllGroupsUseCase = get(),
            getAllLocationsForGroupUseCase = get(),
            addGroupUseCase = get(),
            deleteGroupsUseCase = get(),
            editGroupUseCase = get(),
            reverseGeocodingUseCase = get()
        )
    }
}
