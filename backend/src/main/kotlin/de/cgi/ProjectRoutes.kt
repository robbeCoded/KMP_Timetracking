package de.cgi

import de.cgi.data.datasource.ProjectDataSource
import de.cgi.data.models.Project
import de.cgi.data.requests.NewProjectRequest
import de.cgi.data.requests.ProjectByIdRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId
import java.time.LocalDate

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

            val startDate = LocalDate.parse(request.startDate)
            val endDate = LocalDate.parse(request.endDate)
            val userId = ObjectId(request.userId)

            val project = Project(
                name = request.name,
                startDate = startDate,
                endDate = endDate,
                userId = userId,
                description = request.description
            )

            val wasAcknowledged = projectDataSource.insertProject(project)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK)
        }
    }

}

fun Route.getProjects(
    projectDataSource: ProjectDataSource
) {
    authenticate {
        get("project/getAll") {
            val projectList = projectDataSource.getProjects().toList()
            call.respond(HttpStatusCode.OK, projectList)
        }
    }

}

fun Route.getProject(
    projectDataSource: ProjectDataSource
) {
    authenticate {
        get("project/getOne") {
            val projectRequest = call.receiveNullable<ProjectByIdRequest>()
            if (projectRequest != null) {
                val timeEntryId = projectRequest.id
                val bsonId = ObjectId(timeEntryId)
                val project = projectDataSource.getProjectById(bsonId)
                if (project != null) {
                    call.respond(HttpStatusCode.OK, project)
                    return@get
                }
            }
            call.respond(HttpStatusCode.BadRequest, "No time entry with this id found")
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
                    call.respond(HttpStatusCode.OK)
                    return@delete
                }
            }
            call.respond(HttpStatusCode.BadRequest, "No time entry with this id found")
        }
    }


}