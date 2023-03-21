package android.viewmodel

import android.repository.AuthRepositoryImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AuthViewModel : ViewModel(), KoinComponent {
    private val authRepository: AuthRepositoryImpl by inject()

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.signUp(name, email, password)
            // Handle the result
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.signIn(email, password)
            // Handle the result
        }
    }

    fun authenticate() {
        viewModelScope.launch {
            val result = authRepository.authenticate()
            // Handle the result
        }
    }
}

