package de.cgi.common.api

object routes {
    var BASE_URL = "http://localhost:8080" //"http://10.0.2.2:8085"

    val SIGNUP = "$BASE_URL/signup"
    val SIGNIN = "$BASE_URL/signin"
    val AUTHENTICATE = "$BASE_URL/authenticate"

    val NEW_TIME_ENTRY = "$BASE_URL/timeentry/new"
    val GET_TIME_ENTRIES = "$BASE_URL/timeentry/getAll"
    val GET_TIME_ENTRY_BY_ID = "$BASE_URL/timeentry/getOne"
    val DELETE_TIME_ENTRY = "$BASE_URL/timeentry/delete"

    val NEW_PROJECT = "$BASE_URL/project/new"
    val GET_PROJECTS = "$BASE_URL/project/getAll"
    val GET_PROJECT_BY_ID = "$BASE_URL/project/getOne"
    val DELETE_PROJECT = "$BASE_URL/project/delete"

}
expect fun setBaseUrl(url: String)