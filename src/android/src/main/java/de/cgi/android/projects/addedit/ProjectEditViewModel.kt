package de.cgi.android.projects.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.data.model.Project
import de.cgi.common.projects.ProjectEditUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

class ProjectEditViewModel(
    private val useCase: ProjectEditUseCase,
    userRepository: UserRepository,
    var projectId: String,
) : ViewModel() {

    private val userId: String = userRepository.getUserId()

    private val _projectEditState = MutableStateFlow<ResultState<Project?>>(ResultState.Loading)
    val projectEditState: StateFlow<ResultState<Project?>> = _projectEditState

    private val _projectDeleteState = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    val projectDeleteState: StateFlow<ResultState<Boolean>> = _projectDeleteState

    private val _projectFetchState =
        MutableStateFlow<ResultState<Project?>>(ResultState.Loading)
    val projectFetchState: StateFlow<ResultState<Project?>> = _projectFetchState


    private val _startDate = MutableStateFlow<LocalDate?>(null)
    private val startDate: StateFlow<LocalDate?> = _startDate

    private val _endDate = MutableStateFlow<LocalDate?>(null)
    private val endDate: StateFlow<LocalDate?> = _endDate


    private val _description = MutableStateFlow<String?>(null)
    private val description: StateFlow<String?> = _description

    private val _name = MutableStateFlow("")
    private val name: StateFlow<String> = _name

    private val _color = MutableStateFlow<String?>(null)
    private val color: StateFlow<String?> = _color

    private val _billable = MutableStateFlow(false)
    private val billable: StateFlow<Boolean> = _billable

    private var updateJob: Job? = null
    private var deleteJob: Job? = null
    private var getTimeEntryJob: Job? = null

    fun updateProject() {
        updateJob?.cancel()
        updateJob = useCase.updateProject(
            id = projectId,
            name = name.value,
            startDate = startDate.value.toString(),
            endDate = endDate.value.toString(),
            userId = userId,
            description = description.value,
            color = color.value,
            billable = billable.value
        ).onEach {
            _projectEditState.value = it
        }.launchIn(viewModelScope)
    }

    fun deleteProject() {
        deleteJob?.cancel()
        deleteJob = useCase.deleteProject(projectId).onEach {
            _projectDeleteState.value = it
        }.launchIn(viewModelScope)

    }

    fun getProjectById() {
        getTimeEntryJob?.cancel()
        getTimeEntryJob = useCase.getProjectById(projectId, true).onEach { result ->
            when (result) {
                is ResultState.Success -> {
                    _projectFetchState.value = result
                    result.data?.let { project -> updateValues(project) }
                }
                else -> _projectFetchState.value = result
            }
        }.launchIn(viewModelScope)
    }

    private fun updateValues(project: Project) {
        _startDate.value = project.startDate.toLocalDate()
        _endDate.value = project.endDate.toLocalDate()
        _name.value = project.name
        _description.value = project.description
        _color.value = project.color
        _billable.value = project.billable
    }


    fun startDateChanged(startDate: LocalDate) {
        _startDate.value = startDate
    }

    fun endDateChanged(endDate: LocalDate) {
        _endDate.value = endDate
    }


    fun nameChanged(name: String) {
        _name.value = name
    }

    fun descriptionChanged(description: String) {
        _description.value = description
    }

    fun colorChanged(color: String) {
        _color.value= color
    }

    fun billableChanged(billable: Boolean) {
        _billable.value = billable
    }
    fun getStartDate(): LocalDate? = startDate.value
    fun getEndDate(): LocalDate? = endDate.value
    fun getName(): String = name.value
    fun getDescription(): String? = description.value
    fun getColor(): String? = color.value
    fun getBillable(): Boolean = billable.value

}
