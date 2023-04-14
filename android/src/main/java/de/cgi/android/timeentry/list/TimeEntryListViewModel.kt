package de.cgi.android.timeentry.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.android.timeentry.GetProjectsUseCase
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

class TimeEntryListViewModel(
    private val timeEntryListUseCase: TimeEntryListUseCase,
    userRepository: UserRepository,
) : ViewModel() {

    private val userId = userRepository.getUserId()

    private var loadTimeEntriesJob: Job? = null

    private val _listState = MutableStateFlow(TimeEntryListState())
    val listState = _listState.asStateFlow()

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date
    private val _date = MutableStateFlow<LocalDate?>(currentDate)
    private val date: StateFlow<LocalDate?> = _date

    private val _totalDuration = MutableStateFlow(LocalTime(0, 0, 0))
    private val totalDuration: StateFlow<LocalTime> = _totalDuration


    init {
        getTimeEntries()
    }

    fun getTimeEntries() {
        loadTimeEntriesJob?.cancel()
        loadTimeEntriesJob =
            timeEntryListUseCase.getTimeEntries(userId = userId, date = date.value.toString(), true)
                .onEach { resultState ->
                    _listState.update { it.copy(timeEntryListState = resultState) }
                    if (resultState is ResultState.Success) {
                        _totalDuration.value = timeEntryListUseCase.calculateTotalDuration(resultState.data)
                    }
                }.launchIn(viewModelScope)
    }

    fun selectedDateChanged(date: LocalDate) {
        _date.value = date
        getTimeEntries()
    }

    fun getTotalDuration(): LocalTime {
        return totalDuration.value
    }

}
