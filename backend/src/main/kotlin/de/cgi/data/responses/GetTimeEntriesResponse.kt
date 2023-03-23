package de.cgi.data.responses

data class GetTimeEntriesResponse(
    val timestamp: String,
    val id: String,
    val startTime: String,
    val endTime: String,
    val userId: String,
    val description: String?,
    val projectId: String?
)
