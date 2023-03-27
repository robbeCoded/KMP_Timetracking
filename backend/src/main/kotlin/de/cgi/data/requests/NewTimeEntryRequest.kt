package de.cgi.data.requests

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
data class NewTimeEntryRequest(
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val startTime: LocalDateTime,
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val endTime: LocalDateTime,
    val userId: String,
    val projectId: String?,
    val description: String?,
)