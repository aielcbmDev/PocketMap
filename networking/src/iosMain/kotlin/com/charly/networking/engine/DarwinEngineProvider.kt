package com.charly.networking.engine

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

class DarwinEngineProvider {

    fun create(): HttpClientEngine {
        return Darwin.create {
            configureRequest {
                setAllowsCellularAccess(true)
            }
        }
    }
}
