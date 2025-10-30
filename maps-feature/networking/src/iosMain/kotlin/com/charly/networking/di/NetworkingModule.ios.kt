package com.charly.networking.di

import com.charly.networking.engine.DarwinEngineProvider
import com.charly.networking.utils.BuildConfig
import com.charly.networking.utils.BuildConfigImpl
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val networkingPlatformModule: Module
    get() = module {
        single<BuildConfig> {
            BuildConfigImpl()
        }

        single<HttpClientEngine> {
            DarwinEngineProvider().create()
        }
    }
