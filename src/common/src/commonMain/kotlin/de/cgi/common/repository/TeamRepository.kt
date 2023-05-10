package de.cgi.common.repository

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.User
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun newTeam(userId: String, managerId: String, teamMemberIds: List<String>?): Flow<ResultState<Team?>>
    fun updateTeamName(id: String, name: String): Flow<ResultState<Boolean>>

    fun getTeamById(id: String, forceReload: Boolean): Flow<ResultState<Team?>>
    fun deleteTeam(id: String): Flow<ResultState<Boolean>>

    fun getTeamsForUser(userId: String): Flow<ResultState<List<Team>>>

    fun getAllUsers(): Flow<ResultState<List<User>>>

    fun addUsersToTeam(teamId: String, userIds: List<String>): Flow<ResultState<Boolean>>
}