package de.cgi.components.data

import de.cgi.android.util.getWeekStartDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.timeentry.TimeEntryListState
import de.cgi.common.timeentry.TimeEntryListUseCase
import kotlinx.datetime.*

class TimeEntryListStateHolder(
    private val timeEntryListUseCase: TimeEntryListUseCase,
    userRepository: UserRepository,
    projectMapProvider: ProjectMapProvider,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
) {

    private val userId = userRepository.getUserId()

    private var loadTimeEntriesJob: Job? = null

    private val _listState = MutableStateFlow(TimeEntryListState())
    val listState: StateFlow<TimeEntryListState> = _listState

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date
    private val _selectedDate = MutableStateFlow(currentDate)
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _totalDuration = MutableStateFlow(LocalTime(0, 0, 0))
    val totalDuration: StateFlow<LocalTime> = _totalDuration

    init {
        getTimeEntries()
        projectMapProvider.notifyProjectUpdates()
    }

    fun notifyTimeEntryUpdates() {
        getTimeEntries()
    }

    fun getTimeEntries() {
        loadTimeEntriesJob?.cancel()
        loadTimeEntriesJob = coroutineScope.launch {
            timeEntryListUseCase.getTimeEntries(userId = userId, date = getWeekStartDate(selectedDate.value).toString(), true)
                .collect { resultState ->
                    _listState.value = _listState.value.copy(timeEntryListState = resultState)
                    if (resultState is ResultState.Success) {
                        _totalDuration.value = timeEntryListUseCase.calculateTotalDuration(resultState.data, selectedDate.value)
                    }
                }
        }
    }

    fun selectedDateChanged(date: LocalDate) {
        _selectedDate.value = date
        getTimeEntries()
    }

    fun getTotalDuration(): LocalTime {
        return totalDuration.value
    }

    fun onCleared() {
        coroutineScope.coroutineContext[Job]?.cancel()
    }
}
