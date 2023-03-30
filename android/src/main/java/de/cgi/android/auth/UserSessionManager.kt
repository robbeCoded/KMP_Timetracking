package de.cgi.android.auth

class UserSessionManager {
    private var isLoggedIn = false

    fun login() {
        isLoggedIn = true
    }

    fun logout() {
        isLoggedIn = false
    }

    fun isLoggedIn(): Boolean {
        return isLoggedIn
    }
}
