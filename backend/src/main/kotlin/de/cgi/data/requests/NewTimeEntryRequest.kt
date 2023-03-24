package de.cgi.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class NewTimeEntryRequest(
    val startTime: String,
    val endTime: String,
    val projectId: String?,
    val userId: String,
    val description: String?,
)
