package de.cgi.common.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class NewProjectRequest(
    val name: String,
    val startDate: String,
    val endDate: String,
    val userId: String,
    val description: String?
)
