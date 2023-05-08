package de.cgi.android.di

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
import de.cgi.common.di.commonModule
import org.kodein.di.*


fun appModule(context: Context) = DI.Module("appModule") {
    import(commonModule)
    bind<SharedPreferences>() with singleton { provideSharedPreferences(context) }
    bind<KeyValueStorage>() with singleton { KeyValueStorage(instance()) }
    bind<UserRepository>() with singleton { UserRepository(instance()) }

    bindProvider { SignInViewModel(instance(), instance()) }
    bindProvider { SignUpViewModel(instance(), instance()) }
    //bindProvider { TimeEntryListViewModel(instance(), instance(), instance()) }
    bindProvider { TimeEntryAddViewModel(instance(), instance(), instance()) }

    bindProvider { ProjectListViewModel(instance(), instance()) }
    bindProvider { ProjectAddViewModel(instance(), instance()) }
    bind<TimeEntryListViewModel>() with singleton { TimeEntryListViewModel(instance(), instance(), instance()) }

    bindProvider { DashboardViewModel(instance(), instance(), instance()) }
    bindProvider { TeamListViewModel(instance(), instance()) }
    bindProvider { TeamAddViewModel(instance(), instance()) }
    bindProvider { TeamDashboardViewModel(instance(), instance(), instance(), instance()) }

    bindFactory { projectId: String -> ProjectEditViewModel(instance(), instance(), projectId) }
    bindFactory { timeEntryId: String -> TimeEntryEditViewModel(instance(), instance(), instance(), timeEntryId) }
}

fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
}
