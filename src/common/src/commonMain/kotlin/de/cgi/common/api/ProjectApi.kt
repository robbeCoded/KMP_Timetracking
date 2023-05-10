package de.cgi.common.api

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.Project
import de.cgi.common.data.model.requests.NewProjectRequest
import de.cgi.common.data.model.requests.ProjectRequest
import de.cgi.common.data.model.requests.UpdateProjectRequest
import kotlinx.coroutines.flow.Flow

interface ProjectApi {
    fun newProject(project: NewProjectRequest): Flow<ResultState<Project?>>
    fun updateProject(projectRequest: UpdateProjectRequest): Flow<ResultState<Project?>>
    fun getProjectsForUser(userId: String): Flow<ResultState<List<Project>>>
    fun getProjectById(projectRequest: ProjectRequest): Flow<ResultState<Project?>>
    fun deleteProject(projectRequest: ProjectRequest): Flow<ResultState<Boolean>>
}
