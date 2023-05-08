package de.cgi.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTeamNameRequest(
    val name: String,
    val id: String
)
