package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.api.TeamApi
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.requests.AddTeamManagersRequest
import de.cgi.common.data.model.requests.NewTeamRequest
import de.cgi.common.data.model.requests.RemoveTeamManagerRequest
import de.cgi.common.data.model.requests.UpdateTeamNameRequest
import kotlinx.coroutines.flow.Flow

class TeamRepositoryImpl(
    private val api: TeamApi
) : TeamRepository {

    override fun newTeam(name: String, managerIds: List<String>?): Flow<ResultState<Team?>> {
        val newTeamRequest = NewTeamRequest(name, managerIds)
        return api.newTeam(newTeamRequest)
    }

    override fun updateTeamName(id: String, name: String): Flow<ResultState<Boolean>> {
        val updateTeamNameRequest = UpdateTeamNameRequest(id, name)
        return api.updateTeamName(updateTeamNameRequest)
    }

    override fun addTeamManagers(teamId: String, managerIds: List<String>): Flow<ResultState<Boolean>> {
        val addTeamManagersRequest = AddTeamManagersRequest(teamId, managerIds)
        return api.addManagers(addTeamManagersRequest)
    }

    override fun removeTeamManager(teamId: String, managerId: String): Flow<ResultState<Boolean>> {
        val removeTeamManagerRequest = RemoveTeamManagerRequest(teamId, managerId)
        return api.removeManager(removeTeamManagerRequest)
    }

    override fun getTeamById(id: String, forceReload: Boolean): Flow<ResultState<Team?>> {
           return api.getTeam(id)
    }

    override fun deleteTeam(id: String): Flow<ResultState<Boolean>> {
        return api.deleteTeam(id)
    }
}