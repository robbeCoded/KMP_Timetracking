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

    private val _timeEntryAddState = MutableStateFlow<ResultState<TimeEntry?>>(ResultState.Loading)
    actual val timeEntryAddState: StateFlow<ResultState<TimeEntry?>> = _timeEntryAddState

    private val _listState = MutableStateFlow(ProjectListState())
    actual val listState = _listState.asStateFlow()

    private val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin"))

    private val _startTime =
        MutableStateFlow<LocalTime?>(LocalTime(currentDateTime.hour, currentDateTime.minute))
    actual val startTime: StateFlow<LocalTime?> = _startTime

    private val _endTime = MutableStateFlow<LocalTime?>(
        LocalTime(
            currentDateTime.hour.plus(1),
            currentDateTime.minute
        )
    ) //Todo: Handle next day case
    actual val endTime: StateFlow<LocalTime?> = _endTime

    private val _duration = MutableStateFlow<LocalTime?>(LocalTime(1, 0))
    actual val duration: StateFlow<LocalTime?> = _duration

    private val _date = MutableStateFlow<LocalDate?>(currentDateTime.date)
    actual val date: StateFlow<LocalDate?> = _date

    private val _description = MutableStateFlow<String?>(null)
    actual val description: StateFlow<String?> = _description

    private val _projectId = MutableStateFlow<String?>(null)
    actual val projectId: StateFlow<String?> = _projectId

    private val _projectName = MutableStateFlow<String?>(null)
    actual val projectName: StateFlow<String?> = _projectName

    private var submitJob: Job? = null
    private var loadProjectsJob: Job? = null

    actual fun submitTimeEntry() {
        submitJob?.cancel()
        submitJob = addUseCase.newTimeEntry(
            date = date.value.toString(),
            startTime = startTime.value.toString(),
            endTime = endTime.value.toString(),
            userId = userId,
            description = description.value,
            projectId = projectId.value,
        ).onEach {
            _timeEntryAddState.value = it
        }.launchIn(jsScope)
    }

    actual fun getProjects() {
        loadProjectsJob?.cancel()
        loadProjectsJob = getProjectsUseCase.getProjects(userId = userId, forceReload = true)
            .onEach { resultState ->
                _listState.update { it.copy(projectListState = resultState) }
            }.launchIn(jsScope)
    }

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

            val newDuration = LocalTime(
                endTime.hour.minus(startTime.value!!.hour),
                endTime.minute.minus(startTime.value!!.minute)
            )

            if (newDuration >= LocalTime(0, 0)) {
                _duration.value = newDuration
            }
        }
    }

    actual fun durationChanged(duration: LocalTime) {
        _duration.value = duration

        val newEndTime = LocalTime(
            startTime.value!!.hour.plus(duration.hour),
            startTime.value!!.minute.plus(duration.minute)
        )

        if (newEndTime <= LocalTime(23, 59)) {
            _endTime.value = newEndTime
        }
    }

    actual fun dateChanged(date: LocalDate) {
        _date.value = date
    }

    actual fun descriptionChanged(description: String) {
        _description.value = description
    }

    actual fun projectChanged(projectId: String?, projectName: String?) {
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
