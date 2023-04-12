package de.cgi

import de.cgi.data.datasource.ProjectDataSource
import de.cgi.data.models.Project
import de.cgi.data.requests.NewProjectRequest
import de.cgi.data.requests.ProjectByIdRequest
import de.cgi.data.requests.UpdateProjectRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId


fun Route.newProject(
    projectDataSource: ProjectDataSource
) {
    authenticate {
        post("project/new") {

            val request = call.receiveNullable<NewProjectRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            //TODO: Fehlerbehandlung - empty Fields etc


            val userId = ObjectId(request.userId)

            val project = Project(
                name = request.name,
                startDate = request.startDate,
                endDate = request.endDate,
                userId = userId,
                description = request.description
            )

            val wasAcknowledged = projectDataSource.insertProject(project)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK, project)
        }
    }

}

fun Route.updateProject(
    projectDataSource: ProjectDataSource
) {
    authenticate {
        post("project/update") {

            val request = call.receiveNullable<UpdateProjectRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            //TODO: Fehlerbehandlung - empty Fields etc


            val userId = ObjectId(request.userId)
            val id = ObjectId(request.id)
            val project = Project(
                id = id,
                name = request.name,
                startDate = request.startDate,
                endDate = request.endDate,
                userId = userId,
                description = request.description
            )

            val wasAcknowledged = projectDataSource.updateProject(project)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK, project)
        }
    }

}

fun Route.getProjects(
    projectDataSource: ProjectDataSource
) {
    authenticate {
        get("project/getAll") {
            val userId = call.parameters["id"]
            if (userId != null) {
                val bsonUserId = ObjectId(userId)
                val projects = projectDataSource.getProjects(bsonUserId)
                call.respond(HttpStatusCode.OK, projects)
            }
            call.respond(HttpStatusCode.BadRequest, "Something went wrong")
        }

    }
}

fun Route.getProject(
    projectDataSource: ProjectDataSource
) {
    authenticate {
        get("project/getOne") {
            val id = call.parameters["id"]
            if (id != null) {
                val bsonId = ObjectId(id)
                val project = projectDataSource.getProjectById(bsonId)
                if (project != null) {
                    call.respond(HttpStatusCode.OK, project)
                    return@get
                }
            }
            call.respond(HttpStatusCode.NotFound, "No time entry with this id found")
        }
    }
}

fun Route.deleteProject(
    projectDataSource: ProjectDataSource
) {
    authenticate {
        delete("project/delete") {
            val projectRequest = call.receiveNullable<ProjectByIdRequest>()
            if (projectRequest != null) {
                val projectId = projectRequest.id
                val bsonId = ObjectId(projectId)
                val project = projectDataSource.deleteProject(bsonId)
                if (project) {
                    call.respond(HttpStatusCode.NoContent)
                    return@delete
                }
            }
            call.respond(HttpStatusCode.NotFound, "No time entry with this id found")
        }
    }
}
