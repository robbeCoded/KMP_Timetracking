package de.cgi.android.timeentry

import de.cgi.common.ResultState
import de.cgi.common.data.model.Project
import de.cgi.common.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow

class TimeEntryGetProjectsUseCase(
    private val projectRepository: ProjectRepository
) {
    fun getProjects(userId: String, forceReload: Boolean): Flow<ResultState<List<Project>>> {
        return projectRepository.getProjects(userId = userId, forceReload = forceReload)
    }
}