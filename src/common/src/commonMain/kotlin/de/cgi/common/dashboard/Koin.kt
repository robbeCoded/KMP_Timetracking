package de.cgi.common.dashboard
/*
import de.cgi.common.UserRepository
import de.cgi.common.api.*
import de.cgi.common.auth.SignInUseCase
import de.cgi.common.auth.SignUpUseCase
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.di.httpClientPlatformModule
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
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs), httpClientPlatformModule())
    }

fun initKoin() = initKoin(enableNetworkLogs = false) {}
fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }

    single { createHttpClient(get(), get(), get(), enableNetworkLogs = enableNetworkLogs) }

    single { CoroutineScope(Dispatchers.Default + SupervisorJob() ) }



    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<TimeEntryRepository> { TimeEntryRepositoryImpl(get()/*, get()*/) }
    single<ProjectRepository> { ProjectRepositoryImpl(get() /*,get()*/) }
    single<TeamRepository> { TeamRepositoryImpl(get()) }

    single { SignUpUseCase(get()) }
    single { SignInUseCase(get()) }
    single { TimeEntryListUseCase(get()) }
    single { TimeEntryEditUseCase(get()) }
    single { ProjectListUseCase(get()) }
    single { ProjectAddUseCase(get()) }
    single { ProjectEditUseCase(get()) }
    single { GetProjectsUseCase(get()) }
    single { TimeEntryAddUseCase(get()) }
    single { DashboardUseCase(get()) }
    single { TeamDashboardUseCase(get(), get()) }

    single<AuthApi>{AuthApiImpl(get())}
    single<ProjectApi>{ProjectApiImpl(get())}
    single<TimeEntryApi>{TimeEntryApiImpl(get())}
    single<TeamApi>{TeamApiImpl(get())}

    single<ProjectMapProvider>{ProjectMapProviderImpl(get(), get(), get())}
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
*/