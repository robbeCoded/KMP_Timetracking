package de.cgi.common.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTeamNameRequest(
    val id: String,
    val name: String
)
