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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

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
        userId: String
    ): Flow<ResultState<Project?>> {
        val project = NewProjectRequest(name, startDate, endDate, userId, description)
        return api.newProject(project).map { result ->
            if (result is ResultState.Success) {
                result.data?.let { database.insertProject(it) }
            }
            result
        }
    }

    override fun updateProject(
        id: String,
        name: String,
        description: String?,
        startDate: String,
        endDate: String,
        userId: String
    ): Flow<ResultState<Project?>> {
        val project =
            UpdateProjectRequest(id, name, startDate, endDate, description, userId)
        return api.updateProject(project).map { result ->
            if (result is ResultState.Success) {
                result.data?.let { database.updateProject(it) }
            }
            result
        }
    }


    override fun getProjects(userId: String, forceReload: Boolean): Flow<ResultState<List<Project>>> {
        val cachedProjects = database.getAllProjects(userId)
        return if (cachedProjects.isNotEmpty() && !forceReload) {
            flowOf(ResultState.Success(cachedProjects))
        } else {
            api.getProjects(userId).map { result ->
                if (result is ResultState.Success) {
                    database.clearProjects()
                    database.createProjects(result.data)
                }
                result
            }
        }
    }

    override fun getProjectById(
        id: String,
        forceReload: Boolean
    ): Flow<ResultState<Project?>> {
        val cachedProject = database.getProjectById(id)
        val projectRequest = ProjectRequest(id = id)
        return if (cachedProject != null && !forceReload) {
            flowOf(ResultState.Success(cachedProject))
        } else {
            api.getProjectById(projectRequest).map { result ->
                if (result is ResultState.Success && result.data != null) {
                    database.deleteProject(id)
                    database.insertProject(result.data)
                }
                result
            }
        }
    }


    override fun deleteProject(id: String): Flow<ResultState<Boolean>> {
        return api.deleteProject(
            ProjectRequest(
                id = id
            )
        ).map { result ->
            if (result is ResultState.Success && result.data) {
                database.deleteProject(id)
            }
            result
        }
    }
}
