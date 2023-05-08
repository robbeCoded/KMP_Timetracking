package de.cgi.common.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual class SignUpViewModel actual constructor(
    useCase: SignUpUseCase,
    authValidationUseCase: AuthValidationUseCase
) {
    private val _signUpState = MutableStateFlow<SignUpState?>(SignUpState.Loading)
    actual val signUpState: StateFlow<SignUpState?>
        get() = _signUpState

    actual fun signUp() {
    }

    actual fun signUpEmailChanged(email: String) {
    }

    actual fun signUpPasswordChanged(password: String) {
    }

    actual fun signUpNameChanged(name: String) {
    }

    actual fun isEmailValid(): Boolean {
        TODO("Not yet implemented")
    }

    actual fun isPasswordValid(): Boolean {
        TODO("Not yet implemented")
    }

}