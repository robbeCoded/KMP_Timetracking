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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class TimeEntryEditViewModel(
    private val useCase: TimeEntryEditUseCase,
    private val userRepository: UserRepository,
    private val timeEntryId: String,
) : ViewModel() {

    private val userId: String = userRepository.getUserId()

    private val _timeEntryEditState = MutableStateFlow<ResultState<TimeEntry?>>(ResultState.Loading)
    val timeEntryEditState: StateFlow<ResultState<TimeEntry?>> = _timeEntryEditState

    private val _timeEntryDeleteState = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    val timeEntryDeleteState: StateFlow<ResultState<Boolean>> = _timeEntryDeleteState

    private val _timeEntryFetchState = MutableStateFlow<ResultState<TimeEntry?>>(ResultState.Loading)
    val timeEntryFetchState: StateFlow<ResultState<TimeEntry?>> = _timeEntryFetchState



    private val _startTime = MutableStateFlow<LocalTime?>(null)
    private val startTime: StateFlow<LocalTime?> = _startTime

    private val _endTime = MutableStateFlow<LocalTime?>(null)
    private val endTime: StateFlow<LocalTime?> = _endTime

    private val _duration = MutableStateFlow<LocalTime?>(null)
    private val duration: StateFlow<LocalTime?> = _duration

    private val _date = MutableStateFlow<LocalDate?>(null)
    private val date: StateFlow<LocalDate?> = _date

    private val _description = MutableStateFlow("")
    private val description: StateFlow<String> = _description

    private val _project = MutableStateFlow("")
    private val project: StateFlow<String> = _project

    private var submitJob: Job? = null
    private var deleteJob: Job? = null
    private var getTimeEntryJob: Job? = null

    fun submitTimeEntry() {
        submitJob?.cancel()
        submitJob = useCase.newTimeEntry(
            startTime = startTime.value.toString(),
            endTime = endTime.value.toString(),
            userId = userId,
            description = description.value,
            projectId = project.value,
        ).onEach {
            _timeEntryEditState.value = it
        }.launchIn(viewModelScope)
    }

    fun deleteTimeEntry() {
        deleteJob?.cancel()
        deleteJob = useCase.deleteTimeEntry(timeEntryId).onEach {
            _timeEntryDeleteState.value = it
        }.launchIn(viewModelScope)

    }

    fun getTimeEntryById() {
        getTimeEntryJob?.cancel()
        getTimeEntryJob = useCase.getTimeEntryById(timeEntryId, true).onEach {
            _timeEntryFetchState.value = it
        }.launchIn(viewModelScope)
    }

    fun startTimeChanged(startTime: LocalTime) {
        _startTime.value = startTime
    }

    fun endTimeChanged(endTime: LocalTime) {
        _endTime.value = endTime
    }

    fun durationChanged(duration: LocalTime) {
        _duration.value = duration
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

}
