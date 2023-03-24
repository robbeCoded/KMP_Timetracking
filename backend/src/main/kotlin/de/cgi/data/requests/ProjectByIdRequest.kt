package de.cgi.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class ProjectByIdRequest(
    val id: String
)
