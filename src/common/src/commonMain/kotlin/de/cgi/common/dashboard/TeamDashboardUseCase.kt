package de.cgi.common.dashboard

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.User
import de.cgi.common.data.model.requests.TeamTimeEntriesRequest
import de.cgi.common.repository.TeamRepository
import de.cgi.common.repository.TimeEntryRepository
import kotlinx.coroutines.flow.Flow


class TeamDashboardUseCase(
    private val teamRepository: TeamRepository,
    private val timeEntryRepository: TimeEntryRepository
) {
    fun getAllTeamsForUser(userId: String): Flow<ResultState<List<Team>>> {
        return teamRepository.getTeamsForUser(userId)
    }

    fun getTeamTimeEntriesForWeek(request: TeamTimeEntriesRequest): Flow<ResultState<List<List<TimeEntry>?>>>{
        println("HELLO FROM USECASE GET TEAM TIME ETNRIES")
        return timeEntryRepository.getTeamTimeEntriesForWeek(request, true)
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