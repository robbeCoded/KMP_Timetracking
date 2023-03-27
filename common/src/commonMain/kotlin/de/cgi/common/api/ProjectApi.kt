package de.cgi.common.api

import de.cgi.common.data.model.requests.NewProjectRequest
import io.ktor.client.statement.*

interface ProjectApi {
    suspend fun newProject(project: NewProjectRequest, token: String): HttpResponse
    suspend fun getProjects(token: String): HttpResponse
    suspend fun getProjectById(id: String, token: String): HttpResponse
    suspend fun deleteProject(id: String, token: String): HttpResponse
}
