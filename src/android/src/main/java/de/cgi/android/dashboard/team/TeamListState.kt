package de.cgi.android.dashboard.team

import de.cgi.common.ResultState
import de.cgi.common.data.model.Team

data class TeamListState(
    val teamListState: ResultState<List<Team>> = ResultState.Loading,
)