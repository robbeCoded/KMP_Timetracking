package de.cgi.data.requests

data class RemoveTeamManagerRequest(
    val managerId: String,
    val teamId: String
)
