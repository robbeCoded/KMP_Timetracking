package de.cgi.common.data.model.requests

data class NewTeamRequest(
    val name: String,
    val managerIds: List<String>? = null
)
