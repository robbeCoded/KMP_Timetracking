package de.cgi.android.di

import android.content.Context
import android.content.SharedPreferences
import de.cgi.android.model.AndroidSharedPreferencesStorage
import de.cgi.android.auth.AuthRepositoryImpl
import de.cgi.android.auth.AuthViewModel
import de.cgi.android.auth.UserSessionManager
import de.cgi.android.timetracking.AddTimeEntryViewModel
import de.cgi.android.timetracking.TimeEntryViewModel
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.repository.AuthRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { UserSessionManager() }
    single { provideSharedPreferences(androidContext()) }
    single<KeyValueStorage>{ AndroidSharedPreferencesStorage(get()) }
    single<AuthRepository>{ AuthRepositoryImpl(get(), get()) }

    viewModel{ AuthViewModel(get()) }
    viewModel{ TimeEntryViewModel(get(), get()) }
    viewModel{ AddTimeEntryViewModel(get(), get()) }
}


fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
}
