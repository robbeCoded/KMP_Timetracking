package de.cgi.common.projects

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.Project
import de.cgi.common.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow

class ProjectListUseCase (
    private val repository: ProjectRepository
) {
    fun deleteProject(id: String): Flow<ResultState<Boolean>> {
        return repository.deleteProject(id)
    }
    fun getProjects(userId: String, forceReload: Boolean): Flow<ResultState<List<Project>>> {
        return repository.getProjectsForUser(userId = userId, forceReload = forceReload)
    }

}