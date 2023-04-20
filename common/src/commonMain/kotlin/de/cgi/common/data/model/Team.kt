package de.cgi.common.data.model

data class Team(
    val name: String,
    val id: String,
    var managerIds: List<String>
)
