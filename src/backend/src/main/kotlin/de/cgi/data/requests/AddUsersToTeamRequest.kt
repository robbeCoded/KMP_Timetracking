package de.cgi.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddUsersToTeamRequest(
    val teamId: String,
    val userIds: List<String>
)