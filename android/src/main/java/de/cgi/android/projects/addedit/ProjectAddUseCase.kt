package de.cgi.android.projects.addedit

import de.cgi.common.ResultState
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
        userId: String
    ): Flow<ResultState<Project?>> {
        return repository.newProject(name, description, startDate, endDate, userId)
    }
}