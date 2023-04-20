package de.cgi.common.data.model.requests

data class AddTeamManagersRequest(
    val teamId: String,
    val managerIds: List<String>
)