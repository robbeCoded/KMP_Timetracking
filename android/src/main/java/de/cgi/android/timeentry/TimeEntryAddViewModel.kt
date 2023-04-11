package de.cgi.android.timeentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.data.model.TimeEntry
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.*

class TimeEntryAddViewModel(
    private val useCase: TimeEntryEditUseCase,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val userId: String = userRepository.getUserId()

    private val _timeEntryEditState = MutableStateFlow<ResultState<TimeEntry?>>(ResultState.Loading)
    val timeEntryEditState: StateFlow<ResultState<TimeEntry?>> = _timeEntryEditState

    private val _timeEntryDeleteState = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    val timeEntryDeleteState: StateFlow<ResultState<Boolean>> = _timeEntryDeleteState

    private val _timeEntryFetchState =
        MutableStateFlow<ResultState<TimeEntry?>>(ResultState.Loading)
    val timeEntryFetchState: StateFlow<ResultState<TimeEntry?>> = _timeEntryFetchState

    private val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin"))

    private val _startTime = MutableStateFlow<LocalTime?>(LocalTime(currentDateTime.hour, currentDateTime.minute))
    private val startTime: StateFlow<LocalTime?> = _startTime

    private val _endTime = MutableStateFlow<LocalTime?>(LocalTime(currentDateTime.hour.plus(1), currentDateTime.minute))
    private val endTime: StateFlow<LocalTime?> = _endTime

    private val _duration = MutableStateFlow<LocalTime?>(LocalTime(1,0))
    private val duration: StateFlow<LocalTime?> = _duration

    private val _date = MutableStateFlow<LocalDate?>(currentDateTime.date)
    private val date: StateFlow<LocalDate?> = _date

    private val _description = MutableStateFlow<String?>(null)
    private val description: StateFlow<String?> = _description

    private val _project = MutableStateFlow<String?>(null)
    private val project: StateFlow<String?> = _project

    private var submitJob: Job? = null

    fun submitTimeEntry() {
        submitJob?.cancel()
        submitJob = useCase.newTimeEntry(
            date = date.value.toString(),
            startTime = startTime.value.toString(),
            endTime = endTime.value.toString(),
            userId = userId,
            description = description.value,
            projectId = project.value,
        ).onEach {
            _timeEntryEditState.value = it
        }.launchIn(viewModelScope)
    }

    fun updateTimeEntry() {
        //not needed here
    }

    fun deleteTimeEntry() {
        //Not needed here
    }

    fun getTimeEntryById() {
        //Not needed here
    }

    private fun updateValues(timeEntry: TimeEntry) {
        //Not needed here
    }


    fun startTimeChanged(startTime: LocalTime) {
        _startTime.value = startTime
        _endTime.value = LocalTime(startTime.hour.plus(duration.value!!.hour), startTime.minute.plus(duration.value!!.minute))
    }

    fun endTimeChanged(endTime: LocalTime) {
        _endTime.value = endTime
        _duration.value = (LocalTime(endTime.hour.minus(startTime.value!!.hour), endTime.minute.minus(startTime.value!!.minute)))
    }

    fun durationChanged(duration: LocalTime) {
        _duration.value = duration
        _endTime.value = LocalTime(startTime.value!!.hour.plus(duration.hour), startTime.value!!.minute.plus(duration.minute))
    }

    fun dateChanged(date: LocalDate) {
        _date.value = date
    }

    fun descriptionChanged(description: String) {
        _description.value = description
    }

    fun projectChanged(project: String) {
        _project.value = project
    }

    fun getStartTime(): LocalTime? = startTime.value
    fun getEndTime(): LocalTime? = endTime.value
    fun getDuration(): LocalTime? = duration.value
    fun getDate(): LocalDate? = date.value
    fun getDescription(): String? = description.value
    fun getProject(): String? = project.value

}
