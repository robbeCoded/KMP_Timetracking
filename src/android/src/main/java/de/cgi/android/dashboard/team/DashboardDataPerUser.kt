package de.cgi.android.dashboard.team

import de.cgi.android.dashboard.DashboardDataPerProject

data class DashboardDataPerUser(
    val userId: String,
    val data: List<DashboardDataPerProject>
)

