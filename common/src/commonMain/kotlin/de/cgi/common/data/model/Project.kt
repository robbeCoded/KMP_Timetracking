package de.cgi.common.data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val id: String,
    val userId: String
)
