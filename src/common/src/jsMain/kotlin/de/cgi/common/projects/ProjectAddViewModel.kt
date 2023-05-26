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
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

actual class ProjectAddViewModel actual constructor(
    private val useCase: ProjectAddUseCase,
    userRepository: UserRepository
) {
    private val jsScope = MainScope()
    private val userId: String = userRepository.getUserId()

    private val _projectAddState = MutableStateFlow<ResultState<Project?>>(ResultState.Loading)

    private val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin")).date

    private val _startDate = MutableStateFlow<LocalDate?>(currentDate)
    actual val startDate: StateFlow<LocalDate?> = _startDate

    private val _endDate = MutableStateFlow<LocalDate?>(currentDate)
    actual val endDate: StateFlow<LocalDate?> = _endDate

    private val _name = MutableStateFlow("")
    actual val name: StateFlow<String> = _name

    private val _description = MutableStateFlow<String?>(null)
    actual val description: StateFlow<String?> = _description

    private val _color = MutableStateFlow("#E4E4E4")
    actual val color: StateFlow<String> = _color

    private val _billable = MutableStateFlow(false)
    actual val billable: StateFlow<Boolean> = _billable

    private var submitJob: Job? = null

    actual fun submitProject() {
        submitJob?.cancel()
        submitJob = useCase.newProject(
            startDate = startDate.value.toString(),
            endDate = endDate.value.toString(),
            name = name.value,
            description = description.value,
            userId = userId,
            color = color.value,
            billable = billable.value
        ).onEach {
            _projectAddState.value = it
        }.launchIn(jsScope)
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
        _color.value= color
    }

    actual fun billableChanged(billable: Boolean) {
        _billable.value = billable
    }

    actual fun getStartDate(): LocalDate? = startDate.value
    actual fun getEndDate(): LocalDate? = endDate.value
    actual fun getName(): String = name.value
    actual fun getDescription(): String? = description.value
    actual fun getColor(): String = color.value
    actual fun getBillable(): Boolean = billable.value

}