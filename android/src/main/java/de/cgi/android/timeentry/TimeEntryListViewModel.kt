package de.cgi.android.timeentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.data.model.TimeEntry
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

class TimeEntryListViewModel(
    private val timeEntryListUseCase: TimeEntryListUseCase,
    private val getProjectsUseCase: TimeEntryGetProjectsUseCase,
    userRepository: UserRepository,
) : ViewModel() {

    private val userId = userRepository.getUserId()

    private var loadTimeEntriesJob: Job? = null
    private var deleteTimeEntryJob: Job? = null

    private val _listState = MutableStateFlow(TimeEntryListState())
    val listState = _listState.asStateFlow()

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date
    private val _date = MutableStateFlow<LocalDate?>(currentDate)
    private val date: StateFlow<LocalDate?> = _date

    private val _totalDuration = MutableStateFlow<LocalTime>(LocalTime(0,0,0))
    private val totalDuration: StateFlow<LocalTime> = _totalDuration


    private var loadProjectsJob: Job? = null

    init {
        getTimeEntries()
        getProjectMapping()
    }

    fun getTimeEntries() {
        loadTimeEntriesJob?.cancel()
        loadTimeEntriesJob = timeEntryListUseCase.getTimeEntries(userId = userId, date = date.value.toString(), true).onEach { resultState ->
            _listState.update { it.copy(timeEntryListState = resultState) }
            if (resultState is ResultState.Success) {
                calculateTotalDuration(resultState.data)
            }
        }.launchIn(viewModelScope)
    }


    fun deleteTimeEntry(timeEntry: TimeEntry) {
        deleteTimeEntryJob?.cancel()
        deleteTimeEntryJob = timeEntryListUseCase.deleteTimeEntry(timeEntry.id).onEach {
            _listState.update {
                it.copy(removeTimeEntryState = null)
            }
        }.launchIn(viewModelScope)
    }

    fun getProjectMapping() {
        loadProjectsJob?.cancel()
        loadProjectsJob = getProjectsUseCase.getProjects(userId = userId, forceReload = true)
            .onEach { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        val projectMap = resultState.data.associateBy({ it.id }, { it.name })
                        _listState.update { it.copy(projectMapState = ResultState.Success(projectMap)) }
                    }
                    is ResultState.Error -> {
                        _listState.update { it.copy(projectMapState = resultState) }
                    }
                    is ResultState.Loading -> {
                        _listState.update { it.copy(projectMapState = resultState) }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun calculateTotalDuration(timeEntries: List<TimeEntry>) {
        var totalDurationInSeconds = 0L
        timeEntries.forEach { timeEntry ->
            val startTime = timeEntry.startTime.toLocalTime()
            val endTime = timeEntry.endTime.toLocalTime()
            val durationInSeconds = endTime.toSecondOfDay() - startTime.toSecondOfDay()
            totalDurationInSeconds += durationInSeconds
        }
        _totalDuration.value = LocalTime.fromSecondOfDay(totalDurationInSeconds.toInt())
    }

    fun selectedDateChanged(date: LocalDate) {
        _date.value = date
        getTimeEntries()
    }

    fun getTotalDuration(): LocalTime {return totalDuration.value}

}
