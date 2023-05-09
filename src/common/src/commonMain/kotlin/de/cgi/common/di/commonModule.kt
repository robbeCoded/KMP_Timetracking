package de.cgi.common.di

import de.cgi.common.api.*
import de.cgi.common.auth.AuthValidationUseCase
import de.cgi.common.auth.SignInUseCase
import de.cgi.common.auth.SignUpUseCase
import de.cgi.common.dashboard.DashboardUseCase
import de.cgi.common.dashboard.TeamDashboardUseCase
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.projects.ProjectAddUseCase
import de.cgi.common.projects.ProjectEditUseCase
import de.cgi.common.projects.ProjectListUseCase
import de.cgi.common.repository.*
import de.cgi.common.timeentry.GetProjectsUseCase
import de.cgi.common.timeentry.TimeEntryAddUseCase
import de.cgi.common.timeentry.TimeEntryEditUseCase
import de.cgi.common.timeentry.TimeEntryListUseCase
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.kodein.di.*

val commonModule = DI.Module("common") {
    import(httpClientPlatformModule())
    bind<Json>() with singleton { createJson() }

    bind<HttpClient>() with singleton { createHttpClient(instance(), instance(), instance(), enableNetworkLogs = false) }

    bind<CoroutineScope>() with singleton { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    bind<AuthRepository>() with singleton { AuthRepositoryImpl(instance(), instance()) }
    bind<TimeEntryRepository>() with singleton { TimeEntryRepositoryImpl(instance()) }
    bind<ProjectRepository>() with singleton { ProjectRepositoryImpl(instance()) }
    bind<TeamRepository>() with singleton { TeamRepositoryImpl(instance()) }

    bind<SignUpUseCase>() with provider { SignUpUseCase(instance()) }
    bind<SignInUseCase>() with provider { SignInUseCase(instance()) }
    bind<AuthValidationUseCase>() with provider { AuthValidationUseCase() }
    bind<TimeEntryListUseCase>() with provider { TimeEntryListUseCase(instance()) }
    bind<TimeEntryEditUseCase>() with provider { TimeEntryEditUseCase(instance()) }
    bind<ProjectListUseCase>() with provider { ProjectListUseCase(instance()) }
    bind<ProjectAddUseCase>() with provider { ProjectAddUseCase(instance()) }
    bind<ProjectEditUseCase>() with provider { ProjectEditUseCase(instance()) }
    bind<GetProjectsUseCase>() with provider { GetProjectsUseCase(instance()) }
    bind<TimeEntryAddUseCase>() with provider { TimeEntryAddUseCase(instance()) }
    bind<DashboardUseCase>() with provider { DashboardUseCase(instance()) }
    bind<TeamDashboardUseCase>() with provider { TeamDashboardUseCase(instance(), instance()) }

    bind<AuthApi>() with singleton { AuthApiImpl(instance()) }
    bind<ProjectApi>() with singleton { ProjectApiImpl(instance()) }
    bind<TimeEntryApi>() with singleton { TimeEntryApiImpl(instance()) }
    bind<TeamApi>() with singleton { TeamApiImpl(instance()) }

    bind<ProjectMapProvider>() with singleton { ProjectMapProviderImpl(instance(), instance(), instance()) }
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