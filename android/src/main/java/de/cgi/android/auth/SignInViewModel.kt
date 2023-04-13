package de.cgi.android.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SignInViewModel (
    private val useCase: AuthUseCase,
    userRepository: UserRepository
) : ViewModel(){

    val userId = userRepository.getUserId()

    private val _signInState = MutableStateFlow<SignInState?>(null)
    val signInState: StateFlow<SignInState?> = _signInState

    private val _signInEmail = MutableStateFlow("")
    private val signInEmail: StateFlow<String> = _signInEmail

    private val _signInPassword = MutableStateFlow("")
    private val signInPassword: StateFlow<String> = _signInPassword

    private var signInJob: Job? = null
    private var authJob: Job? = null

    init {
        if(userId.isNotEmpty()) {
            authenticate()
        }
    }
    fun signIn() {
        signInJob?.cancel()
        signInJob = useCase.signIn(email = signInEmail.value, password = signInPassword.value).onEach {
            _signInState.value = it
        }.launchIn(viewModelScope)
    }

    private fun authenticate() {
        authJob?.cancel()
        authJob = useCase.authenticate().onEach {
            _signInState.value = it
        }.launchIn(viewModelScope)
    }

    fun signInEmailChanged(email: String) {
        _signInEmail.value = email
    }
    fun signInPasswordChanged(password: String) {
        _signInPassword.value = password
    }
}