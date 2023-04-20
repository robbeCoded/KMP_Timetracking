package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.api.TeamApi
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.User
import de.cgi.common.data.model.requests.*
import kotlinx.coroutines.flow.Flow

class TeamRepositoryImpl(
    private val api: TeamApi
) : TeamRepository {

    override fun newTeam(userId: String, managerIds: List<String?>): Flow<ResultState<Team?>> {
        val newTeamRequest = NewTeamRequest(userId, managerIds)
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

    override fun getTeamsForUser(userId: String): Flow<ResultState<List<Team>>> {
        return api.getTeamsForUser(userId)
    }

    override fun getAllUsers(): Flow<ResultState<List<User>>> {
        return api.getAllUsers()
    }

    override fun addUsersToTeam(teamId: String, userIds: List<String>): Flow<ResultState<Boolean>> {
        val request = AddUsersToTeamRequest(teamId, userIds)
        return api.addUsersToTeam(request)
    }
}