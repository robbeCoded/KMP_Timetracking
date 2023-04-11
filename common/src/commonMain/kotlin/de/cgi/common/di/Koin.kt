package de.cgi.common.di

import de.cgi.common.api.*
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.platformModule
import de.cgi.common.repository.AuthRepository
import de.cgi.common.repository.AuthRepositoryImpl
import de.cgi.common.repository.TimeEntryRepository
import de.cgi.common.repository.TimeEntryRepositoryImpl
import io.ktor.client.plugins.auth.*

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs), platformModule())
    }

fun initKoin() = initKoin(enableNetworkLogs = false) {}
fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }

    single { createHttpClient(get(), get(), get(), enableNetworkLogs = enableNetworkLogs) }

    single { CoroutineScope(Dispatchers.Default + SupervisorJob() ) }

    single<AuthApi>{AuthApiImpl(get())}
    single<ProjectApi>{ProjectApiImpl(get())}
    single<TimeEntryApi>{TimeEntryApiImpl(get())}
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }


fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, keyValueStorage: KeyValueStorage, enableNetworkLogs: Boolean) = HttpClient(httpClientEngine) {
    install(ContentNegotiation) {
        json(json)
    }
    defaultRequest {
        val token = keyValueStorage.getString("jwt", "") ?: ""
        if (token.isNotEmpty()) {
            headers.append(HttpHeaders.Authorization, "Bearer $token")
        }
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
}
