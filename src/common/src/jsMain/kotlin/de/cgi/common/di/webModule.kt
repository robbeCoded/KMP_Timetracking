package de.cgi.common.di

import de.cgi.common.UserRepository
import de.cgi.common.auth.SignInViewModel
import de.cgi.common.auth.SignUpViewModel
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.di.commonModule
import de.cgi.common.projects.ProjectListViewModel
import de.cgi.common.timeentry.TimeEntryAddViewModel
import de.cgi.common.timeentry.TimeEntryEditViewModel
import de.cgi.common.timeentry.TimeEntryListViewModel
import kotlinx.browser.localStorage
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import org.w3c.dom.Storage

val webModule = DI.Module("webModule") {
    import(commonModule)
    bind<KeyValueStorage>() with singleton { KeyValueStorage(provideLocalStorage()) }
    bind<UserRepository>() with singleton { UserRepository(provideLocalStorage()) }

    bind<SignUpViewModel>() with singleton { SignUpViewModel(instance(), instance()) }
    bind<SignInViewModel>() with singleton { SignInViewModel(instance(), instance()) }
    bind<TimeEntryListViewModel>() with singleton { TimeEntryListViewModel(instance(), instance(), instance()) }
    bind<TimeEntryAddViewModel>() with singleton { TimeEntryAddViewModel(instance(), instance(), instance()) }
    bind<ProjectListViewModel>() with singleton { ProjectListViewModel(instance(), instance()) }

    bind<TimeEntryEditViewModel>() with singleton { TimeEntryEditViewModel(instance(), instance(), instance(), "") }
}

fun provideLocalStorage(): Storage {
    return localStorage
}

