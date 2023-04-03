package de.cgi.android.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import de.cgi.android.model.AndroidSharedPreferencesStorage
import de.cgi.android.auth.AuthRepositoryImpl
import de.cgi.android.auth.AuthViewModel
import de.cgi.android.auth.UserSessionManager
import de.cgi.android.timetracking.addedittimeentry.AddEditTimeEntryViewModel
import de.cgi.android.timetracking.TimeEntryViewModel
import de.cgi.common.cache.DatabaseDriverFactory
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.repository.AuthRepository
import de.cgi.common.repository.TimeEntryRepository
import de.cgi.common.repository.TimeEntryRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.M)
val appModule = module {
    single { UserSessionManager() }
    single { provideDatabaseDriverFactory( androidContext())}
    single { provideSharedPreferences(androidContext()) }
    single<KeyValueStorage>{ AndroidSharedPreferencesStorage(get()) }
    single<AuthRepository>{ AuthRepositoryImpl(get(), get()) }
    single<TimeEntryRepository>{ TimeEntryRepositoryImpl(get(), get())}

    viewModel{ AuthViewModel( androidContext(), get(), get()) }
    viewModel{ TimeEntryViewModel(get(), get()) }
    viewModel{ AddEditTimeEntryViewModel(get(), get()) }
}

fun provideDatabaseDriverFactory(context: Context): DatabaseDriverFactory {
    return DatabaseDriverFactory(context)
}
fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
}
