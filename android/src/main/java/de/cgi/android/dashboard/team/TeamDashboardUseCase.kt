package de.cgi.android.dashboard.team

import de.cgi.android.dashboard.DashboardData
import de.cgi.common.ResultState
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.User
import de.cgi.common.repository.TeamRepository
import kotlinx.coroutines.flow.Flow


class TeamDashboardUseCase(
    private val teamRepository: TeamRepository,
) {
    fun getAllTeamsForUser(userId: String): Flow<ResultState<List<Team>>> {
        return teamRepository.getTeamsForUser(userId)
    }

    fun getAllUsers(): Flow<ResultState<List<User>>> {
        return teamRepository.getAllUsers()
    }

    fun newTeam(teamName: String, managerId: String, teamMemberIds: List<String>?): Flow<ResultState<Team?>> {
        return teamRepository.newTeam(teamName, managerId, teamMemberIds)
    }

    fun addUsersToTeam(teamId: String, userIds: List<String>): Flow<ResultState<Boolean>> {
        return teamRepository.addUsersToTeam(teamId, userIds)
    }



}