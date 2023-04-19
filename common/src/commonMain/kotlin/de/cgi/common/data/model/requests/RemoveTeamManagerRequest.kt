package de.cgi.common.data.model.requests

data class RemoveTeamManagerRequest(
    val teamId: String,
    val managerId: String
)