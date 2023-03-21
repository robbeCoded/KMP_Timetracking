package android.di

import android.AndroidHttpClient
import android.api.AndroidAuthApi
import android.repository.AuthRepositoryImpl
import android.viewmodel.AuthViewModel
import de.cgi.common.AuthApi
import de.cgi.common.repository.AuthRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf


import org.koin.dsl.module


val androidModule = module {
    single { AndroidHttpClient().create() }
    single <AuthApi> {AndroidAuthApi(get())}
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    viewModelOf(::AuthViewModel)
}

