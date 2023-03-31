package de.cgi.common.data.model

import kotlinx.datetime.LocalDateTime

data class TimeEntryWithObjects(
    val timestamp: LocalDateTime,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val id: String,
    val projectId: String?,
    val description: String?,
    val userId: String
)
