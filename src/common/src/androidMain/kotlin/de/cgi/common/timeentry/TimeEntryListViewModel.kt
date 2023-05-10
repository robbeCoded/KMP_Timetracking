package de.cgi.common.timeentry


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.util.getWeekStartDate
import de.cgi.common.util.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.repository.ProjectMapProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

actual class TimeEntryListViewModel actual constructor(
    private val timeEntryListUseCase: TimeEntryListUseCase,
    userRepository: UserRepository,
    projectMapProvider: ProjectMapProvider
) : ViewModel() {

    private val userId = userRepository.getUserId()

    private var loadTimeEntriesJob: Job? = null

    private val _listState = MutableStateFlow(TimeEntryListState())
    actual val listState = _listState.asStateFlow()

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date
    private val _selectedDate = MutableStateFlow(currentDate)
    private val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _totalDuration = MutableStateFlow(LocalTime(0, 0, 0))
    private val totalDuration: StateFlow<LocalTime> = _totalDuration

    actual val updateTrigger: MutableState<Boolean> = mutableStateOf(false)

    init {
        getTimeEntries()
        projectMapProvider.notifyProjectUpdates()
    }

    actual fun getTimeEntries() {
        loadTimeEntriesJob?.cancel()
        loadTimeEntriesJob =
            timeEntryListUseCase.getTimeEntries(userId = userId, date = getWeekStartDate(selectedDate.value).toString(), true)
                .onEach { resultState ->
                    _listState.update { it.copy(timeEntryListState = resultState) }
                    if (resultState is ResultState.Success) {
                        _totalDuration.value = timeEntryListUseCase.calculateTotalDuration(resultState.data, selectedDate.value)
                    }
                }.launchIn(viewModelScope)
    }

    actual fun notifyTimeEntryUpdates() {
        updateTrigger.value = !updateTrigger.value
    }

    actual fun selectedDateChanged(date: LocalDate) {
        _selectedDate.value = date
        getTimeEntries()
    }

    actual fun getTotalDuration(): LocalTime {
        return totalDuration.value
    }
}
