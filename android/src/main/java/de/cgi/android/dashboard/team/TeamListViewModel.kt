package de.cgi.android.dashboard.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class TeamListViewModel(
    private val teamDashboardUseCase: TeamDashboardUseCase,
    userRepository: UserRepository,
) : ViewModel() {

    private val userId = userRepository.getUserId()

    private var loadTeamsJob: Job? = null

    private val _listState = MutableStateFlow(TeamListState())
    val listState = _listState.asStateFlow()

    init {
        getTeams()
    }

    fun getTeams() {
        loadTeamsJob?.cancel()
        loadTeamsJob =
            teamDashboardUseCase.getAllTeamsForUser(userId = userId)
                .onEach { resultState ->
                    _listState.update { it.copy(teamListState = resultState) }
                }.launchIn(viewModelScope)
    }
}