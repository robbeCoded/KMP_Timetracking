package de.cgi.common.projects

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.Project
import de.cgi.common.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow

class ProjectAddUseCase(
    private val repository: ProjectRepository
) {
    fun newProject(
        name: String,
        startDate: String,
        endDate: String,
        description: String?,
        userId: String,
        color: String?,
        billable: Boolean
    ): Flow<ResultState<Project?>> {
        return repository.newProject(name, description, startDate, endDate, userId, color, billable)
    }
}