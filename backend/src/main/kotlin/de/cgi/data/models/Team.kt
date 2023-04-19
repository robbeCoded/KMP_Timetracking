package de.cgi.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Team(
    val name: String,
    @BsonId val id: ObjectId = ObjectId(),
    var managerIds: List<ObjectId>
)
