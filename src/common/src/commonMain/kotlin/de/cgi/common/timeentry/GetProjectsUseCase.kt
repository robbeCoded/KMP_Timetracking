package de.cgi.common.timeentry

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.Project
import de.cgi.common.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow

class GetProjectsUseCase(
    private val projectRepository: ProjectRepository
) {
    fun getProjects(userId: String, forceReload: Boolean): Flow<ResultState<List<Project>>> {
        return projectRepository.getProjectsForUser(userId = userId, forceReload = forceReload)
    }
}