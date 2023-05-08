package de.cgi.data.datasource

import de.cgi.data.models.User
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.`in`
import org.litote.kmongo.setValue

class MongoUserDataSource(
    db: CoroutineDatabase
) : UserDataSource {

    private val users = db.getCollection<User>()

    override suspend fun getUserByEmail(email: String): User? {
        return users.findOne(User::email eq email)
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }

    override suspend fun getAllUsers(): List<User> {
        return users.find().toList()
    }

    override suspend fun addTeamToUser(teamId: String, userIds: List<String>): Boolean {
        val objectIdUserIds = userIds.map { ObjectId(it) }
        val teamIdBson = ObjectId(teamId)
        val updateResult = users.updateMany(
            User::id `in` objectIdUserIds,
            setValue(User::teamId, teamIdBson)
        )
        return updateResult.wasAcknowledged()
    }
}
