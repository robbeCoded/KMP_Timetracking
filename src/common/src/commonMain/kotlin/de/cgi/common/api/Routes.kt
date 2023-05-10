package de.cgi.common.api

object Routes {
    //To reach the server from the Android Emulator
    var BASE_URL = "http://10.0.2.2:8082"

    //To reach the server from the web page
    //var BASE_URL = "http://127.0.0.1:8082"

    val SIGNUP = "$BASE_URL/signup"
    val SIGNIN = "$BASE_URL/signin"
    val AUTHENTICATE = "$BASE_URL/authenticate"
    val GET_ROLE = "$BASE_URL/role"

    val NEW_TIME_ENTRY = "$BASE_URL/timeentry/new"
    val UPDATE_TIME_ENTRY = "$BASE_URL/timeentry/update"
    val GET_TIME_ENTRIES_FOR_WEEK = "$BASE_URL/timeentry/getAllForWeek"
    val GET_TEAM_TIME_ENTRIES_FOR_WEEK = "$BASE_URL/timeentry/getAllForTeamWeek"
    val GET_TIME_ENTRY_BY_ID = "$BASE_URL/timeentry/getOne"
    val DELETE_TIME_ENTRY = "$BASE_URL/timeentry/delete"

    val NEW_PROJECT = "$BASE_URL/project/new"
    val UPDATE_PROJECT = "$BASE_URL/project/update"
    val GET_PROJECTS = "$BASE_URL/project/getProjectsForUser"
    val GET_PROJECT_BY_ID = "$BASE_URL/project/getOne"
    val DELETE_PROJECT = "$BASE_URL/project/delete"

    val NEW_TEAM = "$BASE_URL/team/new"
    val UPDATE_TEAM_NAME = "$BASE_URL/team/updateName"
    val GET_TEAM = "$BASE_URL/team/getOne"
    val DELETE_TEAM = "$BASE_URL/team/delete"
    val GET_TEAMS_FOR_USER = "$BASE_URL/team/teamsForUser"
    val GET_ALL_USERS = "$BASE_URL/users"
    val ADD_USERS_TO_TEAM = "$BASE_URL/team/addUsers"
}

expect fun setBaseUrl(url: String)