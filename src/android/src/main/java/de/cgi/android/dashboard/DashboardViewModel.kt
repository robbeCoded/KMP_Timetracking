package de.cgi.android.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.timeentry.TimeEntryListUseCase
import de.cgi.android.util.getWeekStartDate
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.dashboard.DashboardDataPerProject
import de.cgi.common.dashboard.DashboardDataState
import de.cgi.common.dashboard.DashboardUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

class DashboardViewModel(
    private val dashboardUseCase: DashboardUseCase,
    private val timeEntryListUseCase: TimeEntryListUseCase,
    userRepository: UserRepository
) : ViewModel() {

    private val userId = userRepository.getUserId()

    private var loadTimeEntriesJob: Job? = null
    private var getTeamsJob: Job? = null

    private val _listState = MutableStateFlow(DashboardDataState())
    val listState = _listState.asStateFlow()

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date
    private val _selectedDate = MutableStateFlow(currentDate)
    private val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _totalDuration = MutableStateFlow(LocalTime(0, 0, 0))
    private val totalDuration: StateFlow<LocalTime> = _totalDuration

    private val _projectSummaries = MutableStateFlow<List<DashboardDataPerProject>>(emptyList())
    val projectSummaries: StateFlow<List<DashboardDataPerProject>> = _projectSummaries

    init {
        getTimeEntries()
    }

    fun getTimeEntries() {
        loadTimeEntriesJob?.cancel()
        loadTimeEntriesJob =
            timeEntryListUseCase.getTimeEntries(
                userId = userId,
                date = getWeekStartDate(selectedDate.value).toString(),
                true
            )
                .onEach { resultState ->
                    if (resultState is ResultState.Success) {
                        _totalDuration.value = timeEntryListUseCase.calculateTotalDuration(
                            resultState.data,
                            selectedDate.value
                        )
                        val summaries = dashboardUseCase.processData(resultState.data)
                        if (!summaries.isNullOrEmpty()) {
                            _listState.value = DashboardDataState(
                                dashboardDataState = resultState,
                                dashboardData = summaries
                            )
                        }
                    } else {
                        _listState.update { it.copy(dashboardDataState = resultState) }
                    }
                }.launchIn(viewModelScope)
    }

    fun updateSelectedDateAndReloadMinus() {
        _selectedDate.value = selectedDate.value.minus(DatePeriod(days = 7))
        getTimeEntries()
    }

    fun updateSelectedDateAndReloadPlus() {
        _selectedDate.value = selectedDate.value.plus(DatePeriod(days = 7))
        getTimeEntries()
    }

    fun getSelectedDate(): LocalDate {
        return selectedDate.value
    }

}