package de.cgi.android.di

import android.content.Context
import android.content.SharedPreferences
import de.cgi.android.dashboard.DashboardViewModel
import de.cgi.android.dashboard.team.TeamDashboardViewModel
import de.cgi.android.dashboard.team.TeamListViewModel
import de.cgi.android.projects.addedit.ProjectAddViewModel
import de.cgi.android.projects.addedit.ProjectEditViewModel

import de.cgi.common.UserRepository
import de.cgi.common.auth.SignInViewModel
import de.cgi.common.auth.SignUpViewModel
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.di.commonModule
import de.cgi.common.projects.ProjectListViewModel
import de.cgi.common.timeentry.TimeEntryAddViewModel
import de.cgi.common.timeentry.TimeEntryEditViewModel
import de.cgi.common.timeentry.TimeEntryListViewModel
import org.kodein.di.*

fun appModule(context: Context) = DI.Module("appModule") {
    import(commonModule)
    bind<SharedPreferences>() with singleton { provideSharedPreferences(context) }
    bind<KeyValueStorage>() with singleton { KeyValueStorage(instance()) }
    bind<UserRepository>() with singleton { UserRepository(instance()) }

    bind<SignInViewModel>() with singleton { SignInViewModel(instance(), instance()) }
    bind<SignUpViewModel>() with singleton { SignUpViewModel(instance(), instance()) }
    bind<TimeEntryListViewModel>() with singleton { TimeEntryListViewModel(instance(), instance(), instance()) }
    bind<TimeEntryAddViewModel>() with singleton { TimeEntryAddViewModel(instance(), instance(), instance()) }
    bind<ProjectListViewModel>() with singleton { ProjectListViewModel(instance(), instance()) }
    bind<ProjectAddViewModel>() with singleton { ProjectAddViewModel(instance(), instance()) }
    bind<DashboardViewModel>() with singleton { DashboardViewModel(instance(), instance(), instance()) }
    bind<TeamListViewModel>() with singleton { TeamListViewModel(instance(), instance()) }
    bind<TeamDashboardViewModel>() with singleton { TeamDashboardViewModel(instance(), instance(), instance(), instance()) }

    bind<TimeEntryEditViewModel>() with singleton { TimeEntryEditViewModel(instance(), instance(), instance(), "") }
    bind<ProjectEditViewModel>() with singleton { ProjectEditViewModel(instance(), instance(), "") }
}

fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
}
