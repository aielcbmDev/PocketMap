package com.charly.networking.di

import com.charly.networking.MapsApiService
import com.charly.networking.datasources.ReverseGeocodingDataSource
import com.charly.networking.httpclient.HttpClientFactory
import com.charly.qualifiers.MAPS_API_KEY_DI_NAME
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal expect val networkingPlatformModule: Module

val networkingModule = module {
    includes(networkingPlatformModule)
    factory<HttpClientFactory> {
        HttpClientFactory(
            buildConfig = get(),
            httpClientEngine = get()
        )
    }
    factory<MapsApiService> {
        MapsApiService(
            mapsApiKey = get(named(MAPS_API_KEY_DI_NAME)),
            httpClientFactory = get()
        )
    }
    factory<ReverseGeocodingDataSource> {
        ReverseGeocodingDataSource(
            mapsApiService = get()
        )
    }
}
