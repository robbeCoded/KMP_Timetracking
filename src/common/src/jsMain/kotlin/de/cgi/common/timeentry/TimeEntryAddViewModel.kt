package de.cgi.common.timeentry

import de.cgi.common.UserRepository
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.projects.ProjectListState
import de.cgi.common.util.ResultState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

actual class TimeEntryAddViewModel actual constructor(
    private val addUseCase: TimeEntryAddUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val userRepository: UserRepository
) {
    private val jsScope = MainScope()

    private val userId: String = userRepository.getUserId()

    private val _date = MutableStateFlow<LocalDate?>(null)
    actual val date: StateFlow<LocalDate?> = _date

    private val currentDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    private val _startTime =
        MutableStateFlow<LocalTime?>(currentDateTime.time)
    actual val startTime: StateFlow<LocalTime?> = _startTime

    private val _endTime = MutableStateFlow<LocalTime?>(null)
    actual val endTime: StateFlow<LocalTime?> = _endTime

    private val _duration = MutableStateFlow<LocalTime?>(null)
    actual val duration: StateFlow<LocalTime?> = _duration

    private val _description = MutableStateFlow<String?>(null)
    actual val description: StateFlow<String?> = _description

    private val _projectId = MutableStateFlow<String?>(null)
    actual val projectId: StateFlow<String?> = _projectId

    private val _projectName = MutableStateFlow<String?>(null)
    actual val projectName: StateFlow<String?> = _projectName

    private val _timeEntryAddState = MutableStateFlow<ResultState<TimeEntry?>>(ResultState.Loading)
    actual val timeEntryAddState: StateFlow<ResultState<TimeEntry?>> = _timeEntryAddState

    private val _listState = MutableStateFlow(ProjectListState())
    actual val listState = _listState.asStateFlow()

    private var submitJob: Job? = null
    private var getProjectsJob: Job? = null

    actual fun submitTimeEntry() {
        submitJob?.cancel()
        submitJob =
            addUseCase.newTimeEntry(
                date = date.value.toString(),
                startTime = startTime.value.toString(),
                endTime = endTime.value.toString(),
                userId = userId,
                description = description.value,
                projectId = projectId.value
            ).onEach {
                _timeEntryAddState.value = it
            }.launchIn(jsScope)
    }


    actual fun getProjects() {

        getProjectsJob?.cancel()
        getProjectsJob =
            getProjectsUseCase.getProjects(userId = userId, forceReload = true)
                .onEach { resultState ->
                    _listState.value = _listState.value.copy(projectListState = resultState)
                }.launchIn(jsScope)
    }


    actual fun dateChanged(date: LocalDate) {
        _date.value = date
    }

    actual fun startTimeChanged(startTime: LocalTime) {
        _startTime.value = startTime
    }

    actual fun endTimeChanged(endTime: LocalTime) {
        _endTime.value = endTime
    }

    actual fun durationChanged(duration: LocalTime) {
        _duration.value = duration
    }

    actual fun descriptionChanged(description: String) {
        _description.value = description
    }

    actual fun projectChanged(projectId: String, projectName: String) {
        _projectId.value = projectId
        _projectName.value = projectName
    }

    actual fun getStartTime(): LocalTime? {
        return startTime.value
    }

    actual fun getEndTime(): LocalTime? {
        return endTime.value
    }

    actual fun getDuration(): LocalTime? {
        return duration.value
    }

    actual fun getDate(): LocalDate? {
        return date.value
    }

    actual fun getDescription(): String? {
        return description.value
    }

    actual fun getProjectId(): String? {
        return projectId.value
    }

    actual fun getProjectName(): String? {
        return projectName.value
    }
}
