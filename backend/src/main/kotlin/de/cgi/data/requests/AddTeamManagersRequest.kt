package de.cgi.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddTeamManagersRequest(
    val teamId: String,
    val managerIds: List<String>
)
