package de.cgi.android.auth

interface AuthStateListener {
    fun onAuthChanged(isLoggedIn: Boolean)
}
