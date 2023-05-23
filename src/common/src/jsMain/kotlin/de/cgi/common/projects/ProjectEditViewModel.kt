package de.cgi.common.projects

import de.cgi.common.UserRepository
import de.cgi.common.data.model.Project
import de.cgi.common.util.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

actual class ProjectEditViewModel actual constructor(
    private val useCase: ProjectEditUseCase,
    userRepository: UserRepository,
    var projectId: String,
) {
    private val jsScope = MainScope()

    private val userId: String = userRepository.getUserId()

    private val _projectEditState = MutableStateFlow<ResultState<Project?>>(ResultState.Loading)
    actual val projectEditState: StateFlow<ResultState<Project?>> = _projectEditState

    private val _projectDeleteState = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    actual val projectDeleteState: StateFlow<ResultState<Boolean>> = _projectDeleteState

    private val _projectFetchState =
        MutableStateFlow<ResultState<Project?>>(ResultState.Loading)
    actual val projectFetchState: StateFlow<ResultState<Project?>> = _projectFetchState

    private val _startDate = MutableStateFlow<LocalDate?>(null)
    actual val startDate: StateFlow<LocalDate?> = _startDate

    private val _endDate = MutableStateFlow<LocalDate?>(null)
    actual val endDate: StateFlow<LocalDate?> = _endDate

    private val _description = MutableStateFlow<String?>(null)
    actual val description: StateFlow<String?> = _description

    private val _name = MutableStateFlow("")
    actual val name: StateFlow<String> = _name

    private val _color = MutableStateFlow<String?>(null)
    actual val color: StateFlow<String?> = _color

    private val _billable = MutableStateFlow(false)
    actual val billable: StateFlow<Boolean> = _billable

    private var updateJob: Job? = null
    private var deleteJob: Job? = null
    private var getTimeEntryJob: Job? = null

    actual fun updateProject() {
        updateJob?.cancel()
        updateJob = useCase.updateProject(
            id = projectId,
            name = _name.value,
            startDate = _startDate.value.toString(),
            endDate = _endDate.value.toString(),
            userId = userId,
            description = _description.value,
            color = _color.value,
            billable = _billable.value
        ).onEach {
            _projectEditState.value = it
        }.launchIn(jsScope)
    }

    actual fun deleteProject() {
        deleteJob?.cancel()
        deleteJob = useCase.deleteProject(projectId).onEach {
            _projectDeleteState.value = it
        }.launchIn(jsScope)
    }

    actual fun getProjectById() {
        getTimeEntryJob?.cancel()
        getTimeEntryJob = useCase.getProjectById(projectId, true).onEach { result ->
            when (result) {
                is ResultState.Success -> {
                    _projectFetchState.value = result
                    result.data?.let { project -> updateValues(project) }
                }
                else -> _projectFetchState.value = result
            }
        }.launchIn(jsScope)
    }

    actual fun updateValues(project: Project) {
        _startDate.value = project.startDate.toLocalDate()
        _endDate.value = project.endDate.toLocalDate()
        _name.value = project.name
        _description.value = project.description
        _color.value = project.color
        _billable.value = project.billable
    }

    actual fun startDateChanged(startDate: LocalDate) {
        _startDate.value = startDate
    }

    actual fun endDateChanged(endDate: LocalDate) {
        _endDate.value = endDate
    }

    actual fun nameChanged(name: String) {
        _name.value = name
    }

    actual fun descriptionChanged(description: String) {
        _description.value = description
    }

    actual fun colorChanged(color: String) {
        _color.value = color
    }

    actual fun billableChanged(billable: Boolean) {
        _billable.value = billable
    }

    actual fun getStartDate(): LocalDate? = _startDate.value

    actual fun getEndDate(): LocalDate? = _endDate.value

    actual fun getName(): String = _name.value

    actual fun getDescription(): String? = _description.value

    actual fun getColor(): String? = _color.value

    actual fun getBillable(): Boolean = _billable.value
}