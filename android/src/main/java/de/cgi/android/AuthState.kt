package de.cgi.android

data class AuthState(
    val isLoading: Boolean = false,
    val signUpName: String = "",
    val signUpEmail: String = "",
    val signUpPassword: String = "",
    val signInEmail: String = "",
    val signInPassword: String = ""
)