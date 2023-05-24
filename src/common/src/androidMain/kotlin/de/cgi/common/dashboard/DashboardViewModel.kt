package de.cgi.common.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.UserRepository
import de.cgi.common.timeentry.TimeEntryListUseCase
import de.cgi.common.util.ResultState
import de.cgi.common.util.getWeekStartDate
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

actual class DashboardViewModel(
    private val dashboardUseCase: DashboardUseCase,
    private val timeEntryListUseCase: TimeEntryListUseCase,
    userRepository: UserRepository
) : ViewModel() {

    private val userId = userRepository.getUserId()

    private var loadTimeEntriesJob: Job? = null

    private val _listState = MutableStateFlow(DashboardDataState())
    actual val listState: StateFlow<DashboardDataState> = _listState.asStateFlow()

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date
    private val _selectedDate = MutableStateFlow(currentDate)

    private val _projectSummaries = MutableStateFlow<List<DashboardDataPerProject>>(emptyList())
    actual val projectSummaries: StateFlow<List<DashboardDataPerProject>> = _projectSummaries.asStateFlow()

    init {
        getTimeEntries()
    }

    actual fun getTimeEntries() {
        loadTimeEntriesJob?.cancel()
        loadTimeEntriesJob =
            timeEntryListUseCase.getTimeEntries(
                userId = userId,
                date = getWeekStartDate(_selectedDate.value).toString(),
                true
            )
                .onEach { resultState ->
                    if (resultState is ResultState.Success) {
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

    actual fun updateSelectedDateAndReloadMinus() {
        _selectedDate.value = _selectedDate.value.minus(DatePeriod(days = 7))
        getTimeEntries()
    }

    actual fun updateSelectedDateAndReloadPlus() {
        _selectedDate.value = _selectedDate.value.plus(DatePeriod(days = 7))
        getTimeEntries()
    }

    actual fun getSelectedDate(): LocalDate {
        return _selectedDate.value
    }
}
