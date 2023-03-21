package de.cgi.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class Project(
    val name: String,
    @BsonId val id: ObjectId = ObjectId(),
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val userId: String,
)
