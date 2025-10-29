package charly.baquero.pocketmap.di

import charly.baquero.pocketmap.AppSecrets
import charly.baquero.pocketmap.AppSecretsImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val appPlatformModule: Module
    get() = module {
        factory<AppSecrets> { AppSecretsImpl() }
    }
