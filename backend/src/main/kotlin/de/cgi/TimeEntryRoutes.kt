package de.cgi

import de.cgi.data.datasource.TimeEntryDataSource
import de.cgi.data.models.TimeEntry
import de.cgi.data.requests.NewTimeEntryRequest
import de.cgi.data.requests.TimeEntryByIdRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.bson.types.ObjectId


fun Route.newTimeEntry(
    timeEntryDataSource: TimeEntryDataSource
) {
    authenticate {
        post("timeentry/new") {

            val request = call.receiveNullable<NewTimeEntryRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            //TODO: Fehlerbehandlung - empty Fields etc

            val userId = ObjectId(request.userId)
            val project = request.projectId?.let {ObjectId(it)}
            val timeEntry = TimeEntry(
                startTime = request.startTime,
                endTime = request.endTime,
                userId = userId,
                description = request.description,
                projectId = project,
                timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
            )

            val wasAcknowledged = timeEntryDataSource.insertTimeEntry(timeEntry)
            if(!wasAcknowledged){
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK, timeEntry)
        }
    }

}

fun Route.getTimeEntries(
    timeEntryDataSource: TimeEntryDataSource
) {
    authenticate {
        get("timeentry/getAll") {
            val timeEntriesList = timeEntryDataSource.getTimeEntries()
            call.respond(HttpStatusCode.OK, timeEntriesList)
        }
    }

}

fun Route.getTimeEntry(
    timeEntryDataSource: TimeEntryDataSource
) {
    authenticate{
        get("timeentry/getOne") {
            val id = call.parameters["id"]
            if ( id != null) {
                val bsonId = ObjectId(id)
                val timeEntry = timeEntryDataSource.getTimeEntryById(bsonId)
                if(timeEntry != null){
                    call.respond(HttpStatusCode.OK, timeEntry)
                    return@get
                }
            }
            call.respond(HttpStatusCode.NotFound, "No time entry with this id found")
        }
    }
}

fun Route.deleteTimeEntry(
    timeEntryDataSource: TimeEntryDataSource
) {
    authenticate{
        delete("timeentry/delete") {
            val id = call.parameters["id"]
            if ( id != null) {
                val bsonId = ObjectId(id)
                val result = timeEntryDataSource.deleteTimeEntry(bsonId)
                if(result){
                    call.respond(HttpStatusCode.NoContent)
                    return@delete
                }
            }
            call.respond(HttpStatusCode.NotFound, "No time entry with this id found")
        }
    }
}