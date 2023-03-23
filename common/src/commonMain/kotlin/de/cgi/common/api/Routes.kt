package de.cgi.common.api

object routes {
    private const val BASE_URL = "http://10.0.2.2:8080" //"http://localhost:8080"

    const val SIGNUP = "$BASE_URL/signup"
    const val SIGNIN = "$BASE_URL/signin"
    const val AUTHENTICATE = "$BASE_URL/authenticate"

    const val NEW_TIME_ENTRY = "$BASE_URL/timeentry/new"
    const val GET_TIME_ENTRIES = "$BASE_URL/timeentry/getAll"
    const val GET_TIME_ENTRY_BY_ID = "$BASE_URL/timeentry/getOne"
    const val DELETE_TIME_ENTRY = "$BASE_URL/timeentry/delete"
}