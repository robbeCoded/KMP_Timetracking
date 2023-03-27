package de.cgi.common.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class TimeEntry(
    val timestamp: LocalDateTime,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val id: String,
    val projectId: String?,
    val description: String?,
    val userId: String
)
