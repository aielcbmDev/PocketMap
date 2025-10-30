package com.charly.networking.httpclient

import com.charly.networking.utils.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val buildConfig: BuildConfig,
    private val httpClientEngine: HttpClientEngine
) {

    fun createHttpClient(): HttpClient {
        return HttpClient(httpClientEngine) {
            install(Logging) {
                logger = Logger.Companion.DEFAULT
                level = if (buildConfig.isDebug()) LogLevel.ALL else LogLevel.NONE
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
            install(ContentNegotiation) {
                json(
                    contentType = ContentType.Companion.Any,
                    json = Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}
