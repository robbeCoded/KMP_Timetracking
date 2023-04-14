package de.cgi.android.projects.addedit

import de.cgi.common.ResultState
import de.cgi.common.data.model.Project
import de.cgi.common.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow

class ProjectEditUseCase(
    private val repository: ProjectRepository
) {

    fun getProjectById(id: String, forceReload: Boolean): Flow<ResultState<Project?>> {
        return repository.getProjectById(id, forceReload)
    }

    fun deleteProject(id: String): Flow<ResultState<Boolean>> {
        return repository.deleteProject(id)
    }


    fun updateProject(
        id: String,
        name: String,
        startDate: String,
        endDate: String,
        description: String?,
        userId: String
    ): Flow<ResultState<Project?>> {
        return repository.updateProject(
            id = id,
            name = name,
            startDate = startDate,
            endDate = endDate,
            description = description,
            userId = userId
        )
    }
}