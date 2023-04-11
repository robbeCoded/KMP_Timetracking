package de.cgi.android.auth

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.repository.AuthRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.M)
class AuthViewModel(
    private val useCase: AuthUseCase
) : ViewModel(){

    private val _signInState = MutableStateFlow<SignInState?>(null)
    val signInState: StateFlow<SignInState?> = _signInState

    private val _signUpState = MutableStateFlow<SignUpState?>(null)
    val signUpState: StateFlow<SignUpState?> = _signUpState

    private val _signUpEmail = MutableStateFlow("")
    private val signUpEmail: StateFlow<String> = _signUpEmail

    private val _signUpPassword = MutableStateFlow("")
    private val signUpPassword: StateFlow<String> = _signUpPassword

    private val _signUpName = MutableStateFlow("")
    private val signUpName: StateFlow<String> = _signUpName

    private val _signInEmail = MutableStateFlow("")
    private val signInEmail: StateFlow<String> = _signInEmail

    private val _signInPassword = MutableStateFlow("")
    private val signInPassword: StateFlow<String> = _signInPassword

    private var signUpJob: Job? = null
    private var signInJob: Job? = null
    private var authJob: Job? = null

    init {
        authenticate()
    }

    fun signUp() {
        signUpJob?.cancel()
        signUpJob = useCase.signUp(
            name = signUpName.value,
            email = signUpEmail.value,
            password = signUpPassword.value
        ).onEach {
            _signUpState.value = it
        }.launchIn(viewModelScope)
    }

    fun signIn() {
        signInJob?.cancel()
        signInJob = useCase.signIn(email = signInEmail.value, password = signInPassword.value).onEach {
            _signInState.value = it
        }.launchIn(viewModelScope)
    }

    fun authenticate() {
        authJob?.cancel()
        authJob = useCase.authenticate().onEach {
            _signInState.value = it
        }.launchIn(viewModelScope)
    }

    fun signUpEmailChanged(email: String) {
        _signUpEmail.value = email
    }

    fun signUpPasswordChanged(password: String) {
        _signUpPassword.value = password
    }

    fun signUpNameChanged(name: String) {
        _signUpName.value = name
    }

    fun signInEmailChanged(email: String) {
        _signInEmail.value = email
    }
    fun signInPasswordChanged(password: String) {
        _signInPassword.value = password
    }

    fun isEmailValid(): Boolean {
        return _signUpEmail.value.contains("@")
    }

    fun isPasswordValid(): Boolean {
        return _signUpPassword.value.length >= 8
    }
}

