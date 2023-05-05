package de.cgi.android.dashboard.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.data.model.Team
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class TeamAddViewModel(
    private val teamDashboardUseCase: TeamDashboardUseCase,
    userRepository: UserRepository,
) : ViewModel() {

    private val userId: String = userRepository.getUserId()

    private val _listState = MutableStateFlow(UserListState())
    val listState = _listState.asStateFlow()

    private val _teamName = MutableStateFlow<String?>(null)
    private val teamName: StateFlow<String?> = _teamName

    private val _newTeam = MutableStateFlow<ResultState<Team?>>(ResultState.Loading)
    private val newTeam: StateFlow<ResultState<Team?>> = _newTeam


    private var getUsersJob: Job? = null
    private var newTeamJob: Job? = null
    private var addUsersToTeamJob: Job? = null

    init {
        getUsers()
    }

    fun getUsers() {
        getUsersJob?.cancel()
        getUsersJob = teamDashboardUseCase.getAllUsers().onEach { resultState ->
            _listState.update { it.copy(userListState = resultState) }
        }.launchIn(viewModelScope)
    }

    fun newTeam(name: String, userIds: List<String>) {
        newTeamJob?.cancel()
        newTeamJob = teamDashboardUseCase.newTeam(name, userId, userIds).onEach { result ->
            when (result) {
                is ResultState.Success -> {
                    _newTeam.value = result
                    result.data?.let { team -> addUsersToTeam(team.id, userIds) }
                }
                else -> _newTeam.value = result
            }
        }.launchIn(viewModelScope)
    }

    private fun addUsersToTeam(teamId: String, userIds: List<String>) {
        addUsersToTeamJob?.cancel()
        addUsersToTeamJob =
            teamDashboardUseCase.addUsersToTeam(teamId, userIds).launchIn(viewModelScope)
    }
}