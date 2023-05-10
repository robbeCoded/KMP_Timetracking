package de.cgi.common.dashboard

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.TimeEntry

data class DashboardDataState(
    val dashboardDataState: ResultState<List<TimeEntry>> = ResultState.Loading,
    val dashboardData: List<DashboardDataPerProject> = emptyList()
)
