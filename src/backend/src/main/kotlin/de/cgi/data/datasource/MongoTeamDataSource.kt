package de.cgi.data.datasource

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import de.cgi.data.models.Project
import de.cgi.data.models.Team
import de.cgi.data.requests.UpdateTeamNameRequest
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase

class MongoTeamDataSource(
    db: CoroutineDatabase
) : TeamDataSource{

    private val teams = db.getCollection<Team>()

    override suspend fun insertTeam(team: Team): Boolean {
        return teams.insertOne(team).wasAcknowledged()
    }

    override suspend fun updateName(request: UpdateTeamNameRequest): Boolean {
        val filter = Team::id eq ObjectId(request.id)
        val update = set(
            Project::name.setTo(request.name),
        )
        val updateResult = teams.updateOne(filter, update)
        return updateResult.wasAcknowledged()
    }

    override suspend fun deleteTeam(id: ObjectId): Boolean {
        return teams.deleteOneById(id).wasAcknowledged()
    }

    override suspend fun getTeam(id: ObjectId): Team? {
        return teams.findOneById(id)
    }

    override suspend fun getTeamsForUser(userId: ObjectId): List<Team> {
        return teams.find(Team::managerId eq userId).toList()
    }
}