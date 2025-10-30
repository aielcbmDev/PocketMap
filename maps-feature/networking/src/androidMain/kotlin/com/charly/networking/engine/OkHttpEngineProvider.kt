package com.charly.networking.engine

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine

class OkHttpEngineProvider {

    fun create(): HttpClientEngine {
        val okHttpConfig = OkHttpConfig()
        return OkHttpEngine(okHttpConfig)
    }
}
