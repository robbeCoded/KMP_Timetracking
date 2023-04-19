package de.cgi

import de.cgi.data.datasource.TeamDataSource
import de.cgi.data.models.Team
import de.cgi.data.requests.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId

fun Route.newTeam(
    teamDataSource: TeamDataSource
) {
    authenticate {
        post("team/new") {
            val request = call.receiveNullable<NewTeamRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val managerIdList = mutableListOf<ObjectId>()
            request.managerIds?.forEach { managerId -> managerIdList.add(ObjectId(managerId)) }

            val team = Team(
                name = request.name,
                managerIds = managerIdList
            )

            val wasAcknowledged = teamDataSource.insertTeam(team)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK, team)
        }
    }
}

fun Route.updateTeamName(
    teamDataSource: TeamDataSource
) {
    authenticate {
        post("team/updateName") {
            val request = call.receiveNullable<UpdateTeamNameRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            // TODO: Error handling - empty fields, etc.

            val team = UpdateTeamNameRequest(
                id = request.id,
                name = request.name
            )

            val wasAcknowledged = teamDataSource.updateName(team)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Route.addTeamManagers(
    teamDataSource: TeamDataSource
) {
    authenticate {
        post("team/addManagers") {
            val request = call.receiveNullable<AddTeamManagersRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            // TODO: Error handling - empty fields, etc.

            val teamId = ObjectId(request.teamId)
            val managerIds = request.managerIds.map { ObjectId(it) }

            val wasAcknowledged = teamDataSource.addManagers(managerIds, teamId)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK)
        }
    }
}
fun Route.removeTeamManager(
    teamDataSource: TeamDataSource
) {
    authenticate {
        post("team/removeManager") {
            val request = call.receiveNullable<RemoveTeamManagerRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val managerId = ObjectId(request.managerId)
            val teamId = ObjectId(request.teamId)

            val wasAcknowledged = teamDataSource.removeManager(managerId, teamId)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK, "Manager removed successfully")
        }
    }
}


fun Route.getTeam(
    teamDataSource: TeamDataSource
) {
    authenticate {
        get("team/getOne") {
            val id = call.parameters["id"]
            if (id != null) {
                val bsonId = ObjectId(id)
                val team = teamDataSource.getTeam(bsonId)
                if (team != null) {
                    call.respond(HttpStatusCode.OK, team)
                    return@get
                }
            }
            call.respond(HttpStatusCode.NotFound, "No team with this id found")
        }
    }
}

fun Route.deleteTeam(
    teamDataSource: TeamDataSource
) {
    authenticate {
        delete("team/delete") {
            val id = call.parameters["id"]
            if (id != null) {
                val bsonId = ObjectId(id)
                val teamDeleted = teamDataSource.deleteTeam(bsonId)
                if (teamDeleted) {
                    call.respond(HttpStatusCode.NoContent)
                    return@delete
                }
            }
            call.respond(HttpStatusCode.NotFound, "No team with this id found")
        }
    }
}
