package de.cgi.android.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import de.cgi.android.auth.signin.SignInUseCase
import de.cgi.android.auth.signin.SignInViewModel
import de.cgi.android.auth.signup.SignUpUseCase
import de.cgi.android.auth.signup.SignUpViewModel
import de.cgi.android.dashboard.DashboardUseCase
import de.cgi.android.dashboard.DashboardViewModel
import de.cgi.android.dashboard.team.TeamAddViewModel
import de.cgi.android.dashboard.team.TeamDashboardUseCase
import de.cgi.android.dashboard.team.TeamDashboardViewModel
import de.cgi.android.dashboard.team.TeamListViewModel
import de.cgi.android.projects.*
import de.cgi.android.projects.addedit.ProjectAddUseCase
import de.cgi.android.projects.addedit.ProjectAddViewModel
import de.cgi.android.projects.addedit.ProjectEditUseCase
import de.cgi.android.projects.addedit.ProjectEditViewModel
import de.cgi.android.projects.list.ProjectListUseCase
import de.cgi.android.projects.list.ProjectListViewModel
import de.cgi.android.timeentry.*
import de.cgi.android.timeentry.addedit.*
import de.cgi.android.timeentry.list.TimeEntryListUseCase
import de.cgi.android.timeentry.list.TimeEntryListViewModel
import de.cgi.common.UserRepository
import de.cgi.common.cache.DatabaseDriverFactory
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.M)
val appModule = module {
    single { provideDatabaseDriverFactory(androidContext()) }
    single { provideSharedPreferences(androidContext()) }

    single { KeyValueStorage(get()) }
    single { UserRepository(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<TimeEntryRepository> { TimeEntryRepositoryImpl(get(), get()) }
    single<ProjectRepository> { ProjectRepositoryImpl(get(), get()) }
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
    single { DashboardUseCase() }
    single { TeamDashboardUseCase(get()) }

    viewModel { SignInViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }


    viewModel { (timeEntryId: String) -> TimeEntryEditViewModel(get(), get(), get(), timeEntryId) }
    viewModel { TimeEntryListViewModel(get(), get()) }
    viewModel { TimeEntryAddViewModel(get(), get(), get()) }


    viewModel { (projectId: String) -> ProjectEditViewModel(get(), get(), projectId) }
    viewModel { ProjectListViewModel(get(), get()) }
    viewModel { ProjectAddViewModel(get(), get()) }

    viewModel { DashboardViewModel(get(), get(), get()) }
    viewModel { TeamListViewModel(get(), get()) }
    viewModel { TeamAddViewModel(get(), get()) }
    viewModel { TeamDashboardViewModel(get(), get(), get(), get()) }
}

fun provideDatabaseDriverFactory(context: Context): DatabaseDriverFactory {
    return DatabaseDriverFactory(context)
}

fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
}
