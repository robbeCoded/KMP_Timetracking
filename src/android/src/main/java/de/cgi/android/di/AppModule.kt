package de.cgi.android.di
/*
//import de.cgi.common.cache.DatabaseDriverFactory
import android.content.Context
import android.content.SharedPreferences
import de.cgi.android.dashboard.DashboardViewModel
import de.cgi.android.dashboard.team.TeamAddViewModel
import de.cgi.android.dashboard.team.TeamDashboardViewModel
import de.cgi.android.dashboard.team.TeamListViewModel
import de.cgi.android.projects.addedit.ProjectAddViewModel
import de.cgi.android.projects.addedit.ProjectEditViewModel
import de.cgi.android.projects.list.ProjectListViewModel
import de.cgi.android.timeentry.addedit.TimeEntryAddViewModel
import de.cgi.android.timeentry.addedit.TimeEntryEditViewModel
import de.cgi.android.timeentry.list.TimeEntryListViewModel
import de.cgi.common.SignInViewModel
import de.cgi.common.UserRepository
import de.cgi.common.auth.SignUpViewModel
import de.cgi.common.data.model.KeyValueStorage
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //single { provideDatabaseDriverFactory(androidContext()) }
    single { provideSharedPreferences(androidContext()) }

    single { KeyValueStorage(get()) }
    single { UserRepository(get()) }

    viewModel { SignInViewModel(get(), get()) }
    viewModel { SignUpViewModel(get(), get()) }


    viewModel { (timeEntryId: String) -> TimeEntryEditViewModel(get(), get(), get(), timeEntryId) }
    viewModel { TimeEntryListViewModel(get(), get(), get()) }
    viewModel { TimeEntryAddViewModel(get(), get(), get()) }

    viewModel { (projectId: String) -> ProjectEditViewModel(get(), get(), projectId) }
    viewModel { ProjectListViewModel(get(), get()) }
    viewModel { ProjectAddViewModel(get(), get()) }

    viewModel { DashboardViewModel(get(), get(), get()) }
    viewModel { TeamListViewModel(get(), get()) }
    viewModel { TeamAddViewModel(get(), get()) }
    viewModel { TeamDashboardViewModel(get(), get(), get(), get()) }
}

/*
fun provideDatabaseDriverFactory(context: Context): DatabaseDriverFactory {
    return DatabaseDriverFactory(context)
}*/

fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
}*/
