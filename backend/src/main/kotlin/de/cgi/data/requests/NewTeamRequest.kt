package de.cgi.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class NewTeamRequest(
    val name: String,
    val managerId: String,
    val teamMemberIds: List<String>?
)