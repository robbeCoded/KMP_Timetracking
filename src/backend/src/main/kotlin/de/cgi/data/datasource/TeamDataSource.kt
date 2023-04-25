package de.cgi.data.datasource

import de.cgi.data.models.Team
import de.cgi.data.requests.UpdateTeamNameRequest
import org.bson.types.ObjectId

interface TeamDataSource {

    suspend fun insertTeam(team: Team): Boolean

    suspend fun updateName(request: UpdateTeamNameRequest): Boolean

    suspend fun deleteTeam(id: ObjectId): Boolean

    suspend fun getTeam(id: ObjectId): Team?
    suspend fun getTeamsForUser(userId: ObjectId): List<Team>

}