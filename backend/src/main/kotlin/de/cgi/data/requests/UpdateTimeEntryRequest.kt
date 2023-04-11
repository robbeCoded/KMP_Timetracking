package de.cgi.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTimeEntryRequest(
    val id: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val projectId: String?,
    val description: String?,
    val userId: String,
)