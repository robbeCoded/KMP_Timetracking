package de.cgi.common.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class TimeEntry(
    val timestamp: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val id: String,
    val projectId: String?,
    val description: String?,
    val userId: String
    )