package de.cgi.data.models

import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class TimeEntry(
    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val timestamp: LocalDateTime,

    @Serializable(with = ObjectIdSerializer::class)
    @BsonId val id: ObjectId = ObjectId(),

    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val startTime: LocalDateTime,

    @Serializable(with = LocalDateTimeIso8601Serializer::class)
    val endTime: LocalDateTime,

    @Serializable(with = ObjectIdSerializer::class)
    val projectId: ObjectId?,

    @Serializable(with = ObjectIdSerializer::class)
    val userId: ObjectId,

    val description: String?,
)
