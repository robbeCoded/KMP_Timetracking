package de.cgi.android.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.android.AuthState
import de.cgi.android.AuthUiEvent

import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.repository.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel(){

    var state by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResult = resultChannel.receiveAsFlow()

   /* init {
        authenticate()
    }*/
    fun onEvent(event: AuthUiEvent) {
        when(event){
            //Sign in
            is AuthUiEvent.SignInEmailChanged -> {
                state = state.copy(signInEmail = event.value)
            }
            is AuthUiEvent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = event.value)
            }
            is AuthUiEvent.SignIn -> {
                signIn()
            }
            //Sign up
            is AuthUiEvent.SignUpNameChanged -> {
                state = state.copy(signUpName = event.value)
            }
            is AuthUiEvent.SignUpEmailChanged -> {
                state = state.copy(signUpEmail = event.value)
            }
            is AuthUiEvent.SignUpPasswordChanged -> {
                state = state.copy(signUpPassword = event.value)
            }
            is AuthUiEvent.SignUp -> {
                signUp()
            }

        }
    }
    private fun signUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepository.signUp(
                name = state.signUpName,
                email = state.signUpEmail,
                password = state.signUpPassword
            )
            resultChannel.send(result)

            state = state.copy(isLoading = false)
        }
    }

    fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepository.signIn(
                email = state.signInEmail,
                password = state.signInPassword
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepository.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}

