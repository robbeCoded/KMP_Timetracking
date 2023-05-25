package de.cgi.common.projects

import de.cgi.common.UserRepository
import de.cgi.common.data.model.Project
import de.cgi.common.util.ResultState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

expect class ProjectEditViewModel(
    useCase: ProjectEditUseCase,
    userRepository: UserRepository,
    projectId: String
) {
    val startDate: StateFlow<LocalDate?>
    val endDate: StateFlow<LocalDate?>
    val description: StateFlow<String?>
    val name: StateFlow<String>
    val color: StateFlow<String>
    val billable: StateFlow<Boolean>

    fun updateProject()
    fun deleteProject()
    fun getProjectById()
    fun updateValues(project: Project)

    fun startDateChanged(startDate: LocalDate)
    fun endDateChanged(endDate: LocalDate)
    fun nameChanged(name: String)
    fun descriptionChanged(description: String)
    fun colorChanged(color: String)
    fun billableChanged(billable: Boolean)

    fun getStartDate(): LocalDate?
    fun getEndDate(): LocalDate?
    fun getName(): String
    fun getDescription(): String?
    fun getColor(): String?
    fun getBillable(): Boolean

}
