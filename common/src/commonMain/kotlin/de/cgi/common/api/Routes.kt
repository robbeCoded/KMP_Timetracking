package de.cgi.common.api

object routes {
    private const val BASE_URL = "http://10.0.2.2:8080" //"http://localhost:8080"
    const val SIGNUP = "$BASE_URL/signup"
    const val SIGNIN = "$BASE_URL/signin"
    const val AUTHENTICATE = "$BASE_URL/authenticate"
}