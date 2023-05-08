package de.cgi.common.api

import de.cgi.common.ErrorEntity
import de.cgi.common.ResultState
import de.cgi.common.data.model.Project
import de.cgi.common.data.model.requests.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProjectApiImpl(
    private val client: HttpClient
) : ProjectApi {

    override fun newProject(project: NewProjectRequest): Flow<ResultState<Project?>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.post(Routes.NEW_PROJECT) {
                contentType(ContentType.Application.Json)
                setBody(project)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(response.body()))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose {  }
        }
    }

    override fun updateProject(projectRequest: UpdateProjectRequest): Flow<ResultState<Project?>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.post(Routes.UPDATE_PROJECT) {
                contentType(ContentType.Application.Json)
                setBody(projectRequest)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(response.body()))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose {  }
        }
    }

    override fun getProjectsForUser(userId: String): Flow<ResultState<List<Project>>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.get(Routes.GET_PROJECTS) {
                parameter("id", userId)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(response.body()))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.bodyAsText()}"))))
            }
            awaitClose {  }
        }
    }

    override fun getProjectById(projectRequest: ProjectRequest): Flow<ResultState<Project?>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.get(Routes.GET_PROJECT_BY_ID) {
                parameter("id", projectRequest.id)
                contentType(ContentType.Application.Json)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(response.body()))
                HttpStatusCode.NotFound -> trySend(ResultState.Success(null))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose {  }
        }
    }

    override fun deleteProject(projectRequest: ProjectRequest): Flow<ResultState<Boolean>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.delete(Routes.DELETE_PROJECT) {
                parameter("id", projectRequest.id)
                contentType(ContentType.Application.Json)
            }
            when(response.status) {
                HttpStatusCode.NoContent -> trySend(element = ResultState.Success<Boolean>(true))
                HttpStatusCode.NotFound -> trySend(element = ResultState.Success<Boolean>(false))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose {  }
        }
    }
}