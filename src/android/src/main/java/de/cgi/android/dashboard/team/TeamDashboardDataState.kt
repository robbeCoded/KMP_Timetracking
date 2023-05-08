package de.cgi.android.dashboard.team

import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry


data class TeamDashboardDataState(
    val teamDashboardDataState: ResultState<List<List<TimeEntry>?>> = ResultState.Loading,
    val teamDashboardData: List<DashboardDataPerUser> = emptyList()
)
