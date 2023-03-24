package de.cgi.data.models

import java.time.LocalDateTime
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class TimeEntry(
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime,

    @Serializable(with = ObjectIdSerializer::class)
    @BsonId val id: ObjectId = ObjectId(),

    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,

    @Serializable(with = LocalDateTimeSerializer::class)
    val endTime: LocalDateTime,

    @Serializable(with = ObjectIdSerializer::class)
    val projectId: ObjectId?,

    @Serializable(with = ObjectIdSerializer::class)
    val userId: ObjectId,

    val description: String?,
)
