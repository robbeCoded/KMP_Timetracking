package de.cgi.common.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual class SignUpViewModel actual constructor(
    private val useCase: SignUpUseCase,
    private val authValidationUseCase: AuthValidationUseCase
): ViewModel() {
    private val _signUpState = MutableStateFlow<SignUpState?>(SignUpState.Loading)
    actual val signUpState: StateFlow<SignUpState?>
        get() = _signUpState

    private val _signUpEmail = MutableStateFlow("")
    private val signUpEmail: StateFlow<String> = _signUpEmail

    private val _signUpPassword = MutableStateFlow("")
    private val signUpPassword: StateFlow<String> = _signUpPassword

    private val _signUpName = MutableStateFlow("")
    private val signUpName: StateFlow<String> = _signUpName

    private var signUpJob: Job? = null

    actual fun signUp() {
        signUpJob?.cancel()
        signUpJob = useCase.signUp(
            name = signUpName.value,
            email = signUpEmail.value,
            password = signUpPassword.value
        ).onEach {
            _signUpState.value = it
        }.launchIn(viewModelScope)
    }

    actual fun signUpEmailChanged(email: String) {
        _signUpEmail.value = email
    }

    actual fun signUpPasswordChanged(password: String) {
        _signUpPassword.value = password
    }

    actual fun signUpNameChanged(name: String) {
        _signUpName.value = name
    }

    actual fun isEmailValid(): Boolean {
        return authValidationUseCase.isEmailValid(signUpEmail.value)
    }

    actual fun isPasswordValid(): Boolean {
        return authValidationUseCase.isPasswordValid(signUpPassword.value)
    }

}