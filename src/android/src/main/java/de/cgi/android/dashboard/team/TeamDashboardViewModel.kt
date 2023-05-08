package de.cgi.android.dashboard.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.dashboard.DashboardDataPerProject
import de.cgi.common.dashboard.DashboardUseCase
import de.cgi.common.timeentry.TimeEntryListUseCase
import de.cgi.android.util.getWeekStartDate
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.dashboard.TeamDashboardUseCase
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.TeamTimeEntriesRequest
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

class TeamDashboardViewModel(
    private val teamDashboardUseCase: TeamDashboardUseCase,
    private val dashboardUseCase: DashboardUseCase,
    private val timeEntryListUseCase: TimeEntryListUseCase,
    userRepository: UserRepository,
) : ViewModel() {

    private val userId = userRepository.getUserId()

    private var loadUserIdsJob: Job? = null
    private var loadDataJob: Job? = null
    private var loadTimeEntriesJob: Job? = null

    private val _teamListState = MutableStateFlow(TeamListState())
    val teamListState = _teamListState.asStateFlow()

    private val _dataListState = MutableStateFlow(TeamDashboardDataState())
    val dataListState = _dataListState.asStateFlow()

    private val _userIds = MutableStateFlow<List<String>>(emptyList())
    private val userIds: StateFlow<List<String>> = _userIds

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date
    private val _selectedDate = MutableStateFlow(currentDate)
    private val selectedDate: StateFlow<LocalDate> = _selectedDate

    init {
        getUserIds()
    }

    fun getUserIds() {
        loadUserIdsJob?.cancel()
        loadUserIdsJob =
            teamDashboardUseCase.getAllTeamsForUser(userId = userId)
                .onEach { resultState ->
                    _teamListState.update { it.copy(teamListState = resultState) }
                    if (resultState is ResultState.Success) {
                        val team =
                            resultState.data.firstOrNull() // Assuming you want to get the first team
                        if (team != null) {
                            val userIds = team.teamMemberIds
                            if (userIds != null) {
                                _userIds.value = userIds
                                println(_userIds.value)
                                fetchTeamDashboardData(_userIds.value)
                            }
                        }
                    }
                }.launchIn(viewModelScope)
        println("Out of coroutine getUserIds")
    }


    fun fetchTeamDashboardData(userIds: List<String>) {
        loadDataJob?.cancel()
        val request = TeamTimeEntriesRequest(userIds, getWeekStartDate(selectedDate.value).toString())
        loadDataJob = teamDashboardUseCase.getTeamTimeEntriesForWeek(request).onEach { resultState ->
            _dataListState.update { it.copy(teamDashboardDataState = resultState) }
            if(resultState is ResultState.Success){
                val dashboardDataPerUserList = mutableListOf<DashboardDataPerUser>()
                resultState.data.forEach {
                    val currentUserId = it?.firstOrNull()?.userId ?: ""
                    val dashboardDataPerProjectList = processTimeEntriesToDashboardData(it) ?: emptyList()
                    dashboardDataPerUserList.add(DashboardDataPerUser(currentUserId, dashboardDataPerProjectList))
                }
                println(dashboardDataPerUserList)
                _dataListState.update { it.copy(teamDashboardData = dashboardDataPerUserList) }
            }
        }.launchIn(viewModelScope)
    }


    private fun processTimeEntriesToDashboardData(
        timeEntries: List<TimeEntry>?,
    ): List<DashboardDataPerProject>? {
        return dashboardUseCase.processData(timeEntries)
    }
}

