package de.cgi.android.projects.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import de.cgi.common.data.model.Project
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


class ProjectAddViewModel(
    private val useCase: ProjectAddUseCase,
    userRepository: UserRepository,
) : ViewModel() {

    private val userId: String = userRepository.getUserId()

    private val _projectAddState = MutableStateFlow<ResultState<Project?>>(ResultState.Loading)
    val projectAddState: StateFlow<ResultState<Project?>> = _projectAddState

    private val _projectDeleteState = MutableStateFlow<ResultState<Boolean>>(ResultState.Loading)
    val projectDeleteState: StateFlow<ResultState<Boolean>> = _projectDeleteState

    private val _projectFetchState =
        MutableStateFlow<ResultState<Project?>>(ResultState.Loading)
    val projectFetchState: StateFlow<ResultState<Project?>> = _projectFetchState

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date

    private val _startDate = MutableStateFlow<LocalDate?>(currentDate)
    private val startDate: StateFlow<LocalDate?> = _startDate

    private val _endDate = MutableStateFlow<LocalDate?>(currentDate)
    private val endDate: StateFlow<LocalDate?> = _endDate

    private val _name = MutableStateFlow("")
    private val name: StateFlow<String> = _name

    private val _description = MutableStateFlow<String?>(null)
    private val description: StateFlow<String?> = _description

    private var submitJob: Job? = null

    fun submitProject() {
        submitJob?.cancel()
        submitJob = useCase.newProject(
            startDate = startDate.value.toString(),
            endDate = endDate.value.toString(),
            name = name.value,
            description = description.value,
            userId = userId
        ).onEach {
            _projectAddState.value = it
        }.launchIn(viewModelScope)
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

    fun getStartDate(): LocalDate? = startDate.value
    fun getEndDate(): LocalDate? = endDate.value
    fun getName(): String = name.value
    fun getDescription(): String? = description.value

}
