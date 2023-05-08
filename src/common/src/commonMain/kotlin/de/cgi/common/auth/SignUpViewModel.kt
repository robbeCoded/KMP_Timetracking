package de.cgi.common.auth

import kotlinx.coroutines.flow.StateFlow

expect class SignUpViewModel(
    useCase: SignUpUseCase,
    authValidationUseCase: AuthValidationUseCase
) {
    val signUpState: StateFlow<SignUpState?>

    fun signUp()
    fun signUpEmailChanged(email: String)
    fun signUpPasswordChanged(password: String)
    fun signUpNameChanged(name: String)
    fun isEmailValid(): Boolean
    fun isPasswordValid(): Boolean
}

