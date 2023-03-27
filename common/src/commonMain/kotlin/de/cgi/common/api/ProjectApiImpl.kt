package de.cgi.common.api

import de.cgi.common.data.model.requests.NewProjectRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ProjectApiImpl(
    private val client: HttpClient
) : ProjectApi {

    override suspend fun newProject(project: NewProjectRequest, token: String): HttpResponse {
        return client.post(routes.NEW_PROJECT) {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(project)
        }
    }

    override suspend fun getProjects(token: String): HttpResponse {
        return client.get(routes.GET_PROJECTS) {
            header("Authorization", "Bearer $token")
        }
    }

    override suspend fun getProjectById(id: String, token: String): HttpResponse {
        return client.get(routes.GET_PROJECT_BY_ID) {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(id)
        }
    }

    override suspend fun deleteProject(id: String, token: String): HttpResponse {
        return client.delete(routes.DELETE_PROJECT) {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(id)
        }
    }
}
