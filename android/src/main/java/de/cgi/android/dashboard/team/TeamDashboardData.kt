package de.cgi.android.dashboard.team

import de.cgi.android.dashboard.DashboardData
import kotlinx.datetime.LocalTime

data class TeamDashboardData(
    val name: String,
    val dataList: List<DashboardData>

)

