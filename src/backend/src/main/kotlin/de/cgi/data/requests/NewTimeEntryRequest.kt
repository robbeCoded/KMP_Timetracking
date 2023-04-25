package de.cgi.data.requests

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
data class NewTimeEntryRequest(
    val date: String,
    val startTime: String,
    val endTime: String,
    val projectId: String?,
    val description: String?,
    val userId: String,
)
