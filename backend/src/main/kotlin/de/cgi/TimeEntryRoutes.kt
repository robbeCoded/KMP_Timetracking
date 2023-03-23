package de.cgi

import de.cgi.data.datasource.TimeEntryDataSource
import de.cgi.data.models.TimeEntry
import de.cgi.data.requests.NewTimeEntry
import de.cgi.data.requests.TimeEntryById
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Route.newTimeEntry(
    timeEntryDataSource: TimeEntryDataSource
) {
    authenticate {
        post("timeentry/new") {

            val request = call.receiveNullable<NewTimeEntry>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            //TODO: Fehlerbehandlung - empty Fields etc
            val formatter = DateTimeFormatter.ofPattern("yy-MM-dd'T'HH:mm:ss")
            val startTime = LocalDateTime.parse(request.startTime, formatter)
            val endTime = LocalDateTime.parse(request.endTime, formatter)
            println(startTime)
            println(endTime)
            val timeEntry = TimeEntry(
                startTime = startTime,
                endTime = endTime,
                userId = request.userId,
                description = request.description,
                project = request.projectId,
                timestamp = LocalDateTime.now()
            )

            val wasAcknowledged = timeEntryDataSource.insertTimeEntry(timeEntry)
            if(!wasAcknowledged){
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK)
        }
    }

}

fun Route.getTimeEntries(
    timeEntryDataSource: TimeEntryDataSource
) {
    authenticate{
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
            val timeEntryRequest = call.receiveNullable<TimeEntryById>()
            if(timeEntryRequest != null) {
                val timeEntryId = timeEntryRequest.id
                println(timeEntryId)
                val bsonId = ObjectId(timeEntryId)
                val timeEntry = timeEntryDataSource.getTimeEntryById(bsonId)
                if(timeEntry != null){
                    println("INSIDE THE IF")
                    call.respond(HttpStatusCode.OK, timeEntry)
                }
            }
            call.respond(HttpStatusCode.BadRequest, "No time entry with this id found")
        }
    }
}

fun Route.deleteTimeEntry(
    timeEntryDataSource: TimeEntryDataSource
) {
    authenticate{
        get("timeentry/delete") {
            val timeEntryRequest = call.receiveNullable<TimeEntryById>()
            if(timeEntryRequest != null) {
                val timeEntryId = timeEntryRequest.id
                val bsonId = ObjectId(timeEntryId)
                val timeEntry = timeEntryDataSource.deleteTimeEntry(bsonId)
                if(timeEntry){
                    call.respond(HttpStatusCode.OK)
                }
            }
            call.respond(HttpStatusCode.BadRequest, "No time entry with this id found")
        }
    }
}