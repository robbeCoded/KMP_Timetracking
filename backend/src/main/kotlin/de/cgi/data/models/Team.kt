package de.cgi.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Team(
    val name: String,
    @Serializable(with = ObjectIdSerializer::class)
    @BsonId val id: ObjectId = ObjectId(),
    @Serializable(with = ObjectIdSerializer::class)
    val managerId: ObjectId,
    val teamMemberIds: List<String>?
)
