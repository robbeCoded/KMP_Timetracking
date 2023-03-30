package de.cgi.common.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimeEntry(
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("start_time")
    val startTime: String,
    @SerialName("end_time")
    val endTime: String,
    @SerialName("id")
    val id: String,
    @SerialName("project_id")
    val projectId: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("user_id")
    val userId: String
)
