package de.cgi.android.dashboard.team

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.User

data class UserListState(
    val userListState: ResultState<List<User>> = ResultState.Loading,
)