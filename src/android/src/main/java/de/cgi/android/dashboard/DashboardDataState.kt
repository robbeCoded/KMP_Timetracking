package de.cgi.android.dashboard

import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry

data class DashboardDataState(
    val dashboardDataState: ResultState<List<TimeEntry>> = ResultState.Loading,
    val dashboardData: List<DashboardDataPerProject> = emptyList()
)
