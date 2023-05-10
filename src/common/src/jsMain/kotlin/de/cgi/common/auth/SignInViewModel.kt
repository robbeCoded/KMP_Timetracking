package de.cgi.common.auth

import de.cgi.common.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual class SignInViewModel actual constructor(
    private val useCase: SignInUseCase,
    userRepository: UserRepository
) {
    private val jsScope = MainScope()

    actual val userId = userRepository.getUserId()

    private val _signInState = MutableStateFlow<SignInState?>(SignInState.Loading)
    actual val signInState: StateFlow<SignInState?> = _signInState

    private val _signInEmail = MutableStateFlow("")
    private val signInEmail: StateFlow<String> = _signInEmail

    private val _signInPassword = MutableStateFlow("")
    private val signInPassword: StateFlow<String> = _signInPassword

    private var signInJob: Job? = null
    private var getUserRoleJob: Job? = null
    private var authJob: Job? = null

    init {
        if (userId.isNotEmpty()) {
            authenticate()
        }
    }

    actual fun signIn() {
        signInJob?.cancel()
        signInJob = useCase.signIn(email = signInEmail.value, password = signInPassword.value).onEach {
            _signInState.value = it
        }.launchIn(jsScope)
    }

    actual fun signInEmailChanged(email: String) {
        _signInEmail.value = email
    }

    actual fun signInPasswordChanged(password: String) {
        _signInPassword.value = password
    }

    actual fun getUserRole() {
        getUserRoleJob?.cancel()
        getUserRoleJob = useCase.getUserRole().onEach {
            _signInState.value = it
        }.launchIn(jsScope)
    }

    private fun authenticate() {
        authJob?.cancel()
        authJob = useCase.authenticate().onEach {
            _signInState.value = it
        }.launchIn(jsScope)
    }
}

