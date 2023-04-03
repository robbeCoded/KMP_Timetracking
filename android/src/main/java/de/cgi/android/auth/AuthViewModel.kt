package de.cgi.android.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.android.util.NetworkUtil
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.repository.AuthRepository
import kotlinx.coroutines.channels.Channel

import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.net.ConnectException


@RequiresApi(Build.VERSION_CODES.M)
class AuthViewModel(
    private val context: Context, //TODO
    private val authRepository: AuthRepository,
    private val prefs: SharedPreferences
) : ViewModel(){

    var state by mutableStateOf(AuthState())


    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResult = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun checkInternetConnection() {
        viewModelScope.launch {
            try {
                if (NetworkUtil.isInternetAvailable(context)) {
                    authenticate()
                } else {
                    val token = prefs.getString("jwt", null)
                    if (token != null) {
                        resultChannel.send(AuthResult.Authorized())
                    } else {
                        resultChannel.send(AuthResult.Unauthorized())
                    }
                }
            } catch (e: ConnectException) {
                resultChannel.send(AuthResult.UnknownError())
            }

        }
    }

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
            val token = prefs.getString("jwt", null)
            if (token != null) {
                resultChannel.send(AuthResult.Authorized())
            } else {
                val result = authRepository.authenticate()
                resultChannel.send(result)
            }
            state = state.copy(isLoading = false)
        }
    }
}

