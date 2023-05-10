package de.cgi.common.timeentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.UserRepository
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.projects.ProjectListState
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.util.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalTime

actual class TimeEntryEditViewModel actual constructor(
    private val editUseCase: TimeEntryEditUseCase,
    private val projectMapProvider: ProjectMapProvider,
    userRepository: UserRepository,
    var timeEntryId: String,
) : ViewModel() {

    private val userId: String = userRepository.getUserId()

    private val _timeEntryEditState = MutableStateFlow<ResultState<TimeEntry?>>(ResultState.Loading)
    actual val timeEntryEditState: StateFlow<ResultState<TimeEntry?>> = _timeEntryEditState

    private val _timeEntryDeleteState = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    actual val timeEntryDeleteState: StateFlow<ResultState<Boolean>> = _timeEntryDeleteState

    private val _timeEntryFetchState =
        MutableStateFlow<ResultState<TimeEntry?>>(ResultState.Loading)
    actual val timeEntryFetchState: StateFlow<ResultState<TimeEntry?>> = _timeEntryFetchState

    private val _listState = MutableStateFlow(ProjectListState())
    actual val listState = _listState.asStateFlow()

    private val _startTime = MutableStateFlow<LocalTime?>(null)
    actual val startTime: StateFlow<LocalTime?> = _startTime

    private val _endTime = MutableStateFlow<LocalTime?>(null)
    actual val endTime: StateFlow<LocalTime?> = _endTime

    private val _duration = MutableStateFlow<LocalTime?>(null)
    actual val duration: StateFlow<LocalTime?> = _duration

    private val _date = MutableStateFlow<LocalDate?>(null)
    actual val date: StateFlow<LocalDate?> = _date

    private val _description = MutableStateFlow<String?>(null)
    actual val description: StateFlow<String?> = _description

    private val _projectId = MutableStateFlow<String?>(null)
    actual val projectId: StateFlow<String?> = _projectId

    private val _projectName = MutableStateFlow<String?>(null)
    actual val projectName: StateFlow<String?> = _projectName


    private var updateJob: Job? = null
    private var deleteJob: Job? = null
    private var getTimeEntryJob: Job? = null

    init {
        projectMapProvider.notifyProjectUpdates()
    }

    actual fun updateTimeEntry() {
        updateJob?.cancel()
        updateJob = editUseCase.updateTimeEntry(
            id = timeEntryId,
            date = date.value.toString(),
            startTime = startTime.value.toString(),
            endTime = endTime.value.toString(),
            userId = userId,
            description = description.value,
            projectId = projectId.value,
        ).onEach {
            _timeEntryEditState.value = it
        }.launchIn(viewModelScope)
    }

    actual fun deleteTimeEntry() {
        deleteJob?.cancel()
        deleteJob = editUseCase.deleteTimeEntry(timeEntryId).onEach {
            _timeEntryDeleteState.value = it
        }.launchIn(viewModelScope)
    }

    actual fun getTimeEntryById() {
        getTimeEntryJob?.cancel()
        getTimeEntryJob = editUseCase.getTimeEntryById(timeEntryId, true).onEach { result ->
            when (result) {
                is ResultState.Success -> {
                    _timeEntryFetchState.value = result
                    result.data?.let { timeEntry -> updateValues(timeEntry) }
                }
                else -> _timeEntryFetchState.value = result
            }
        }.launchIn(viewModelScope)
    }

    actual fun updateValues(timeEntry: TimeEntry) {
        _startTime.value = timeEntry.startTime.toLocalTime()
        _endTime.value = timeEntry.endTime.toLocalTime()
        _duration.value = LocalTime(
            timeEntry.endTime.toLocalTime().hour.minus(timeEntry.startTime.toLocalTime().hour),
            timeEntry.endTime.toLocalTime().minute.minus(timeEntry.startTime.toLocalTime().minute)
        )
        _date.value = timeEntry.date.toLocalDate()
        _description.value = timeEntry.description
        _projectId.value = timeEntry.projectId
        _projectName.value = timeEntry.projectId?.let {
            projectMapProvider.getProjectNameById(
                timeEntry.projectId
            )
        }
    }
    /*TODO Implement notifiers for else cases */
    actual fun startTimeChanged(startTime: LocalTime) {
        _startTime.value = startTime

        val newEndTime = LocalTime(
            startTime.hour.plus(duration.value!!.hour),
            startTime.minute.plus(duration.value!!.minute)
        )

        if (newEndTime <= LocalTime(23, 59)) {
            _endTime.value = newEndTime
        }
    }

    actual fun endTimeChanged(endTime: LocalTime) {
        if (endTime >= startTime.value!!) {
            _endTime.value = endTime
            _duration.value = LocalTime(
                endTime.hour.minus(startTime.value!!.hour),
                endTime.minute.minus(startTime.value!!.minute)
            )
        } else {
            _endTime.value = LocalTime(23, 59)
        }
    }

    actual fun durationChanged(duration: LocalTime) {
        if (startTime.value!!.hour.plus(duration.hour) <= 23 && startTime.value!!.minute.plus(
                duration.minute
            ) <= 59
        ) {
            _duration.value = duration
            _endTime.value = LocalTime(
                startTime.value!!.hour.plus(duration.hour),
                startTime.value!!.minute.plus(duration.minute)
            )
        } else {
            _duration.value = LocalTime(23 - startTime.value!!.hour, 59 - startTime.value!!.minute)
            _endTime.value = LocalTime(23, 59)
        }
    }

    actual fun dateChanged(date: LocalDate) {
        _date.value = date
    }

    actual fun descriptionChanged(description: String) {
        _description.value = description
    }

    actual fun projectChanged(projectId: String, projectName: String) {
        _projectId.value = projectId
        _projectName.value = projectName
    }

    actual fun getStartTime(): LocalTime? = startTime.value
    actual fun getEndTime(): LocalTime? = endTime.value
    actual fun getDuration(): LocalTime? = duration.value
    actual fun getDate(): LocalDate? = date.value
    actual fun getDescription(): String? = description.value
    actual fun getProjectId(): String? = projectId.value
    actual fun getProjectName(): String? = projectName.value
}
