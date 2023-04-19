package de.cgi.android.dashboard

import kotlinx.datetime.LocalTime

data class DashboardData(
    val projectId: String?,
    val duration: LocalTime,
    val percentage: Double
)
