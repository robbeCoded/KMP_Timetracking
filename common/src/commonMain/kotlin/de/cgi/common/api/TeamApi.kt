package de.cgi.common.api

import de.cgi.common.ResultState
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.User
import de.cgi.common.data.model.requests.*
import kotlinx.coroutines.flow.Flow

interface TeamApi {
    fun newTeam(team: NewTeamRequest): Flow<ResultState<Team?>>
    fun updateTeamName(request: UpdateTeamNameRequest): Flow<ResultState<Boolean>>

    fun getTeam(id: String): Flow<ResultState<Team?>>
    fun deleteTeam(id: String): Flow<ResultState<Boolean>>
    fun getTeamsForUser(userId: String): Flow<ResultState<List<Team>>>
    fun getAllUsers(): Flow<ResultState<List<User>>>

    fun addUsersToTeam(request: AddUsersToTeamRequest): Flow<ResultState<Boolean>>

}