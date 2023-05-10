package de.cgi.android.timeentry.addedit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.projects.ProjectListState
import de.cgi.android.ui.components.showToast
import de.cgi.common.util.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.ProjectMapProvider
import de.cgi.common.timeentry.TimeEntryEditUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalTime

class TimeEntryEditViewModel(
    private val editUseCase: TimeEntryEditUseCase,
    private val projectMapProvider: ProjectMapProvider,
    userRepository: UserRepository,
    var timeEntryId: String,
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
    val listState = _listState.asStateFlow()


    private val _startTime = MutableStateFlow<LocalTime?>(null)
    private val startTime: StateFlow<LocalTime?> = _startTime

    private val _endTime = MutableStateFlow<LocalTime?>(null)
    private val endTime: StateFlow<LocalTime?> = _endTime

    private val _duration = MutableStateFlow<LocalTime?>(null)
    private val duration: StateFlow<LocalTime?> = _duration

    private val _date = MutableStateFlow<LocalDate?>(null)
    private val date: StateFlow<LocalDate?> = _date

    private val _description = MutableStateFlow<String?>(null)
    private val description: StateFlow<String?> = _description

    private val _projectId = MutableStateFlow<String?>(null)
    private val projectId: StateFlow<String?> = _projectId

    private val _projectName = MutableStateFlow<String?>(null)
    private val projectName: StateFlow<String?> = _projectName


    private var updateJob: Job? = null
    private var deleteJob: Job? = null
    private var getTimeEntryJob: Job? = null

    init {
        projectMapProvider.notifyProjectUpdates()
    }

    fun updateTimeEntry() {
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

    fun deleteTimeEntry() {
        deleteJob?.cancel()
        deleteJob = editUseCase.deleteTimeEntry(timeEntryId).onEach {
            _timeEntryDeleteState.value = it
        }.launchIn(viewModelScope)

    }

    fun getTimeEntryById() {
        println("Ran the get TimeEntryById with $timeEntryId")
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

    private fun updateValues(timeEntry: TimeEntry) {
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
                timeEntry.projectId!!
            )
        }
    }


    fun startTimeChanged(startTime: LocalTime, context: Context) {
        _startTime.value = startTime
        val newEndTime = LocalTime(
            startTime.hour.plus(duration.value!!.hour),
            startTime.minute.plus(duration.value!!.minute)
        )

        if (newEndTime <= LocalTime(23, 59)) {
            _endTime.value = newEndTime
        } else {
            context.showToast("The selected times exceed the current day. Setting end time to the maximum possible.")
            _endTime.value = LocalTime(23, 59)
        }
    }

    fun endTimeChanged(endTime: LocalTime, context: Context) {
        if (endTime >= startTime.value!!) {
            _endTime.value = endTime
            _duration.value = LocalTime(
                endTime.hour.minus(startTime.value!!.hour),
                endTime.minute.minus(startTime.value!!.minute)
            )
        } else {
            context.showToast("The end time should be after the start time.")
        }
    }

    fun durationChanged(duration: LocalTime, context: Context) {
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
            context.showToast("The selected duration exceeds the current day. Setting duration to the maximum possible.")
            _duration.value = LocalTime(23 - startTime.value!!.hour, 59 - startTime.value!!.minute)
            _endTime.value = LocalTime(23, 59)
        }
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

    fun getProjectName(): String? = projectName.value


}
