package de.cgi.android.timeentry.list

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.android.util.getWeekStartDate
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.timeentry.TimeEntryListState
import de.cgi.common.timeentry.TimeEntryListUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

class TimeEntryListViewModel(
    private val timeEntryListUseCase: TimeEntryListUseCase,
    userRepository: UserRepository,
    projectMapProvider: ProjectMapProvider
) : ViewModel() {

    private val userId = userRepository.getUserId()

    private var loadTimeEntriesJob: Job? = null

    private val _listState = MutableStateFlow(TimeEntryListState())
    val listState = _listState.asStateFlow()

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date
    private val _selectedDate = MutableStateFlow(currentDate)
    private val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _totalDuration = MutableStateFlow(LocalTime(0, 0, 0))
    private val totalDuration: StateFlow<LocalTime> = _totalDuration

    val updateTrigger = mutableStateOf(false)

    init {
        Log.d("TimeEntryListViewModel", "init viewModel")
        getTimeEntries()
        projectMapProvider.notifyProjectUpdates()
    }

    fun notifyTimeEntryUpdates() {
        updateTrigger.value = !updateTrigger.value
    }

    fun getTimeEntries() {

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

    fun selectedDateChanged(date: LocalDate) {
        _selectedDate.value = date
        getTimeEntries()
    }
    fun getTotalDuration(): LocalTime {
        return totalDuration.value
    }

}
