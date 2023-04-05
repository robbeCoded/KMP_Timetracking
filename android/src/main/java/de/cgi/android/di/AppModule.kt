package de.cgi.android.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import de.cgi.android.auth.AuthUseCase
import de.cgi.android.auth.AuthViewModel
import de.cgi.android.timetracking.TimeEntryUseCase
import de.cgi.android.timetracking.TimeEntryViewModel
import de.cgi.common.cache.DatabaseDriverFactory
import de.cgi.common.data.model.KeyValueStorage
import de.cgi.common.repository.AuthRepository
import de.cgi.common.repository.AuthRepositoryImpl
import de.cgi.common.repository.TimeEntryRepository
import de.cgi.common.repository.TimeEntryRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.M)
val appModule = module {
    single { provideDatabaseDriverFactory( androidContext())}
    single { provideSharedPreferences(androidContext()) }

    single{ KeyValueStorage(get()) }

    single<AuthRepository>{ AuthRepositoryImpl(get(), get()) }
    single<TimeEntryRepository>{ TimeEntryRepositoryImpl(get(), get())}

    single {AuthUseCase(get())}
    single {TimeEntryUseCase(get())}

    viewModel{ AuthViewModel(get()) }
    viewModel{ TimeEntryViewModel(get()) }
}

fun provideDatabaseDriverFactory(context: Context): DatabaseDriverFactory {
    return DatabaseDriverFactory(context)
}
fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
}
