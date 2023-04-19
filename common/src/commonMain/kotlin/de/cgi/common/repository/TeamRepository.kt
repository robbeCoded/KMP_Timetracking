package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.data.model.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun newTeam(name: String, managerIds: List<String>?): Flow<ResultState<Team?>>
    fun updateTeamName(id: String, name: String): Flow<ResultState<Boolean>>
    fun addTeamManagers(teamId: String, managerIds: List<String>): Flow<ResultState<Boolean>>
    fun removeTeamManager(teamId: String, managerId: String): Flow<ResultState<Boolean>>
    fun getTeamById(id: String, forceReload: Boolean): Flow<ResultState<Team?>>
    fun deleteTeam(id: String): Flow<ResultState<Boolean>>
}