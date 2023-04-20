package de.cgi.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProjectRequest(
    val id: String,
    val name: String,
    val startDate: String,
    val endDate: String,
    val description: String?,
    val userId: String,
    val color: String?
)