package de.cgi.android.dashboard

import kotlinx.datetime.LocalTime

data class DashboardDataPerProject(
    val projectId: String?,
    val duration: LocalTime,
    val percentage: Double,
)
