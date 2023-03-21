package de.cgi.common.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class NewTimeEntry(
    val startTime: String,
    val endTime: String,
    val userId: String,
    val description: String?,
    val projectId: String?
)
