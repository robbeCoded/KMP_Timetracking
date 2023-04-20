package de.cgi.android.dashboard.team

import de.cgi.common.ResultState


data class TeamDashboardDataState(
    val teamDashboardDataState: ResultState<List<TeamDashboardData>> = ResultState.Loading,
    val teamDashboardData: List<TeamDashboardData> = emptyList()
)
