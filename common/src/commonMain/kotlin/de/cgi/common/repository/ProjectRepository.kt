package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.data.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun newProject(
        name: String,
        description: String?,
        startDate: String,
        endDate: String,
        userId: String,
        color: String?
    ): Flow<ResultState<Project?>>

    fun updateProject(
        id: String,
        name: String,
        description: String?,
        startDate: String,
        endDate: String,
        userId: String,
        color: String?
    ): Flow<ResultState<Project?>>

    fun getProjects(userId: String, forceReload: Boolean): Flow<ResultState<List<Project>>>

    fun getProjectById(id: String, forceReload: Boolean): Flow<ResultState<Project?>>

    fun deleteProject(id: String): Flow<ResultState<Boolean>>
}