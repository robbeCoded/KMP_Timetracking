package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.api.ProjectApi
import de.cgi.common.cache.Database
import de.cgi.common.cache.DatabaseDriverFactory
import de.cgi.common.data.model.Project
import de.cgi.common.data.model.requests.NewProjectRequest
import de.cgi.common.data.model.requests.ProjectRequest
import de.cgi.common.data.model.requests.UpdateProjectRequest
import kotlinx.coroutines.flow.Flow

class ProjectRepositoryImpl(
    databaseDriverFactory: DatabaseDriverFactory,
    private val api: ProjectApi
) : ProjectRepository {

    private val database = Database(databaseDriverFactory)
    override fun newProject(
        name: String,
        description: String?,
        startDate: String,
        endDate: String,
        userId: String,
        color: String?,
        billable: Boolean
    ): Flow<ResultState<Project?>> {
        val project = NewProjectRequest(name, startDate, endDate, userId, description, color, billable)
        return api.newProject(project)
    }

    override fun updateProject(
        id: String,
        name: String,
        description: String?,
        startDate: String,
        endDate: String,
        userId: String,
        color: String?,
        billable: Boolean
    ): Flow<ResultState<Project?>> {
        val project =
            UpdateProjectRequest(id, name, startDate, endDate, description, userId, color, billable)
        return api.updateProject(project)
    }


    override fun getProjectsForUser(
        userId: String,
        forceReload: Boolean
    ): Flow<ResultState<List<Project>>> {
        return api.getProjectsForUser(userId)
    }

    override fun getProjectById(
        id: String,
        forceReload: Boolean
    ): Flow<ResultState<Project?>> {
        val projectRequest = ProjectRequest(id = id)
        return api.getProjectById(projectRequest)
    }


    override fun deleteProject(id: String): Flow<ResultState<Boolean>> {
        return api.deleteProject(
            ProjectRequest(
                id = id
            )
        )
    }
}
