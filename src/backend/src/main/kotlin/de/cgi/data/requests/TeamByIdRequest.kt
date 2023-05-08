package de.cgi.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class TeamByIdRequest(
    val id: String
)
