package de.cgi.common.api

import de.cgi.common.ResultState
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.requests.AddTeamManagersRequest
import de.cgi.common.data.model.requests.NewTeamRequest
import de.cgi.common.data.model.requests.RemoveTeamManagerRequest
import de.cgi.common.data.model.requests.UpdateTeamNameRequest
import kotlinx.coroutines.flow.Flow

interface TeamApi {
    fun newTeam(team: NewTeamRequest): Flow<ResultState<Team?>>
    fun updateTeamName(request: UpdateTeamNameRequest): Flow<ResultState<Boolean>>
    fun addManagers(request: AddTeamManagersRequest): Flow<ResultState<Boolean>>
    fun removeManager(request: RemoveTeamManagerRequest): Flow<ResultState<Boolean>>
    fun getTeam(id: String): Flow<ResultState<Team?>>
    fun deleteTeam(id: String): Flow<ResultState<Boolean>>
}