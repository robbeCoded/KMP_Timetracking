package de.cgi.android.timeentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.android.projects.ProjectListState
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.data.model.TimeEntry
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

class TimeEntryAddViewModel(
    private val addUseCase: TimeEntryAddUseCase,
    private val getProjectsUseCase: TimeEntryGetProjectsUseCase,
    userRepository: UserRepository,
) : ViewModel() {

    private val userId: String = userRepository.getUserId()

    private val _timeEntryEditState = MutableStateFlow<ResultState<TimeEntry?>>(ResultState.Loading)
    val timeEntryEditState: StateFlow<ResultState<TimeEntry?>> = _timeEntryEditState

    private val _timeEntryDeleteState = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    val timeEntryDeleteState: StateFlow<ResultState<Boolean>> = _timeEntryDeleteState

    private val _timeEntryFetchState =
        MutableStateFlow<ResultState<TimeEntry?>>(ResultState.Loading)
    val timeEntryFetchState: StateFlow<ResultState<TimeEntry?>> = _timeEntryFetchState

    private val _listState = MutableStateFlow(ProjectListState())
    val listState =  _listState.asStateFlow()

    private val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin"))

    private val _startTime = MutableStateFlow<LocalTime?>(LocalTime(currentDateTime.hour, currentDateTime.minute))
    private val startTime: StateFlow<LocalTime?> = _startTime

    private val _endTime = MutableStateFlow<LocalTime?>(LocalTime(currentDateTime.hour.plus(1), currentDateTime.minute)) //Todo: Handle next day case
    private val endTime: StateFlow<LocalTime?> = _endTime

    private val _duration = MutableStateFlow<LocalTime?>(LocalTime(1,0))
    private val duration: StateFlow<LocalTime?> = _duration

    private val _date = MutableStateFlow<LocalDate?>(currentDateTime.date)
    private val date: StateFlow<LocalDate?> = _date

    private val _description = MutableStateFlow<String?>(null)
    private val description: StateFlow<String?> = _description

    private val _projectId = MutableStateFlow<String?>(null)
    private val projectId: StateFlow<String?> = _projectId

    private val _projectName = MutableStateFlow<String?>(null)
    private val projectName: StateFlow<String?> = _projectName

    private var submitJob: Job? = null
    private var loadProjectsJob: Job? = null

    fun submitTimeEntry() {
        submitJob?.cancel()
        submitJob = addUseCase.newTimeEntry(
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

    fun getProjects() {
        loadProjectsJob?.cancel()
        loadProjectsJob = getProjectsUseCase.getProjects(userId = userId, forceReload = true).onEach { resultState ->
            _listState.update { it.copy(projectListState = resultState) }
        }.launchIn(viewModelScope)
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

    fun projectChanged(projectId: String, projectName: String) {
        _projectId.value = projectId
        _projectName.value = projectName
    }

    fun getStartTime(): LocalTime? = startTime.value
    fun getEndTime(): LocalTime? = endTime.value
    fun getDuration(): LocalTime? = duration.value
    fun getDate(): LocalDate? = date.value
    fun getDescription(): String? = description.value
    fun getProjectId(): String? = projectId.value

}
