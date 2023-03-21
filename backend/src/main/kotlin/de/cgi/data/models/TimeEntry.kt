package de.cgi.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class TimeEntry(
    val timestamp: LocalDateTime,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    @BsonId val id: ObjectId = ObjectId(),
    val project: String?,
    val description: String?,
    val userId: String
)
