package de.cgi

import de.cgi.data.datasource.TimeEntryDataSource
import de.cgi.data.models.TimeEntry
import de.cgi.data.requests.NewTimeEntry
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Route.newTimeEntry(
    timeEntryDataSource: TimeEntryDataSource
) {
    post("newtimeentry") {

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

fun Route.getTimeEntries(
    timeEntryDataSource: TimeEntryDataSource
) {
    get("timeentries") {
        val timeEntriesList = timeEntryDataSource.getTimeEntries()
        call.respond(HttpStatusCode.OK, timeEntriesList)
    }
}