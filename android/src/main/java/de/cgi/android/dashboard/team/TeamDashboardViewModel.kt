package de.cgi.android.dashboard.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.android.dashboard.DashboardData
import de.cgi.android.dashboard.DashboardUseCase
import de.cgi.android.timeentry.list.TimeEntryListUseCase
import de.cgi.android.util.getWeekStartDate
import de.cgi.common.ErrorEntity
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.data.model.TimeEntry
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.*

class TeamDashboardViewModel(
    private val teamDashboardUseCase: TeamDashboardUseCase,
    private val dashboardUseCase: DashboardUseCase,
    private val timeEntryListUseCase: TimeEntryListUseCase,
    userRepository: UserRepository,
) : ViewModel() {

    private val userId = userRepository.getUserId()

    private var loadTeamsJob: Job? = null
    private var loadDataJob: Job? = null

    private val _teamListState = MutableStateFlow(TeamListState())
    val teamListState = _teamListState.asStateFlow()

    private val _dataListState = MutableStateFlow(TeamDashboardDataState())
    val dataListState = _dataListState.asStateFlow()

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date
    private val _selectedDate = MutableStateFlow(currentDate)
    private val selectedDate: StateFlow<LocalDate> = _selectedDate

    init {
        getTeams()
    }

    fun getTeams() {
        loadTeamsJob?.cancel()
        loadTeamsJob =
            teamDashboardUseCase.getAllTeamsForUser(userId = userId)
                .onEach { resultState ->
                    _teamListState.update { it.copy(teamListState = resultState) }
                    if (resultState is ResultState.Success) {
                        val team =
                            resultState.data.firstOrNull() // Assuming you want to get the first team
                        if (team != null) {
                            val userIds = team.teamMemberIds
                            if (userIds != null) {
                                getData(userIds)
                                println(userIds)
                            }
                        }
                    }
                }.launchIn(viewModelScope)
    }

    suspend fun fetchTeamDashboardData(userIds: List<String>): List<TeamDashboardData> {
        val teamDashboardDataList = mutableListOf<TeamDashboardData>()

        for (userId in userIds) {
            val timeEntriesFlow = timeEntryListUseCase.getTimeEntries(
                userId,
                getWeekStartDate(selectedDate.value).toString(),
                true
            )
            println("collected time entries")

            timeEntriesFlow.collect { resultState ->
                if (resultState is ResultState.Success) {

                    val dashboardData = processTimeEntriesToDashboardData(resultState.data, userId)
                    println("INSIDE THE FOR LOOP" + dashboardData)
                    teamDashboardDataList.add(dashboardData)
                }
            }
        }
        println("Vor dem RETURN" + teamDashboardDataList)
        return teamDashboardDataList
    }


    fun getData(userIds: List<String>) {
        loadDataJob?.cancel()

        loadDataJob = viewModelScope.launch {
            try {
                val combinedDashboardDataList = fetchTeamDashboardData(userIds)

                // Update the dataList state with the combined data
                _dataListState.update {
                    it.copy(
                        teamDashboardData = combinedDashboardDataList,
                        teamDashboardDataState = ResultState.Success(combinedDashboardDataList)
                    )
                }
            } catch (e: Exception) {
                _dataListState.update {
                    it.copy(
                        teamDashboardDataState = ResultState.Error(ErrorEntity())
                    )
                }
            }
        }
    }

    private fun processTimeEntriesToDashboardData(
        timeEntries: List<TimeEntry>,
        userId: String
    ): TeamDashboardData {
        val dashboardData = dashboardUseCase.processData(timeEntries)
        return TeamDashboardData(name = userId, dataList = dashboardData)
    }
}
