package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.User
import de.cgi.common.data.model.requests.AddUsersToTeamRequest
import de.cgi.common.data.model.requests.NewTeamRequest
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun newTeam(userId: String, managerIds: List<String?>): Flow<ResultState<Team?>>
    fun updateTeamName(id: String, name: String): Flow<ResultState<Boolean>>
    fun addTeamManagers(teamId: String, managerIds: List<String>): Flow<ResultState<Boolean>>
    fun removeTeamManager(teamId: String, managerId: String): Flow<ResultState<Boolean>>
    fun getTeamById(id: String, forceReload: Boolean): Flow<ResultState<Team?>>
    fun deleteTeam(id: String): Flow<ResultState<Boolean>>

    fun getTeamsForUser(userId: String): Flow<ResultState<List<Team>>>

    fun getAllUsers(): Flow<ResultState<List<User>>>

    fun addUsersToTeam(teamId: String, userIds: List<String>): Flow<ResultState<Boolean>>
}