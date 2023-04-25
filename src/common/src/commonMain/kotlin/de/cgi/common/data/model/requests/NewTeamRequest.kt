package de.cgi.common.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class NewTeamRequest(
    val name: String,
    val managerId: String,
    val teamMemberIds: List<String>?
)
