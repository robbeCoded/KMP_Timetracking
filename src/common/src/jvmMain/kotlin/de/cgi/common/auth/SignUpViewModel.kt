package de.cgi.common.auth

import kotlinx.coroutines.flow.StateFlow

// In shared module
actual class SignUpViewModel actual constructor(
    useCase: SignUpUseCase,
    authValidationUseCase: AuthValidationUseCase
) {
    actual val signUpState: StateFlow<SignUpState?>
        get() = TODO("Not yet implemented")

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