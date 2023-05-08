package de.cgi.common.auth

//Example validation logic
class AuthValidationUseCase {
    fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }
}