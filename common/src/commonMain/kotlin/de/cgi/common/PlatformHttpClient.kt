package de.cgi.common

import io.ktor.client.*

// commonMain
interface PlatformHttpClient {
    fun create(): HttpClient
}
