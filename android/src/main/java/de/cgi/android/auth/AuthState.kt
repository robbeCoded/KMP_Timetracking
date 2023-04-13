package de.cgi.android.auth

import de.cgi.common.data.model.responses.AuthResponse

sealed class SignInState {
    object Loading : SignInState()
    data class Success(val authResponse: AuthResponse?) : SignInState()
    object Unauthorized : SignInState()
    object Authorized : SignInState()
    data class Failure(val message: String?) : SignInState()
    data class Error(val error: String?) : SignInState()
}

sealed class SignUpState {
    object Loading : SignUpState()
    object Success : SignUpState()
    data class Failure(val message: String?) : SignUpState()
    data class Error(val error: String?) : SignUpState()
}
