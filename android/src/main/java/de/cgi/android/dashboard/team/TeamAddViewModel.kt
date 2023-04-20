package de.cgi.android.dashboard.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.android.projects.list.ProjectListState
import de.cgi.common.UserRepository
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

    private var getUsersJob: Job? = null

    init {
        getUsers()
    }
    fun getUsers() {
        getUsersJob?.cancel()
        getUsersJob = teamDashboardUseCase.getAllUsers().onEach { resultState ->
            _listState.update { it.copy(userListState = resultState) }
        }.launchIn(viewModelScope)
    }
}