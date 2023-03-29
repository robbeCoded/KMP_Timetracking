package de.cgi.common.repository

import de.cgi.common.api.TimeEntryApi
import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.data.model.responses.TimeEntryResponse
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*


class TimeEntryRepositoryImpl(
    private val api: TimeEntryApi
) : TimeEntryRepository{
    override suspend fun newTimeEntry(timeEntry: NewTimeEntry, token: String): AuthResult<TimeEntryResponse?> {
        val response = api.newTimeEntry(timeEntry, token)
        return when (response.status) {
            HttpStatusCode.OK -> {
                AuthResult.Authorized(response.body())
            }
            HttpStatusCode.Unauthorized -> {
                AuthResult.Unauthorized()
            }
            else -> {
                AuthResult.UnknownError()
            }
        }
    }


    override suspend fun getTimeEntries(token: String): AuthResult<List<TimeEntryResponse>> {
        println("HELLO FROM THE REPO")
        val response = api.getTimeEntries(token)
        return when (response.status) {
            HttpStatusCode.OK -> {
                println(response.bodyAsText())
                AuthResult.Authorized(response.body())
            }
            HttpStatusCode.Unauthorized -> {
                AuthResult.Unauthorized()
            }
            else -> {
                AuthResult.UnknownError()
            }
        }


    }

    override suspend fun getTimeEntryById(id: String, token: String): AuthResult<TimeEntryResponse?> {
        val response = api.getTimeEntryById(id, token)
        return when (response.status) {
            HttpStatusCode.OK -> {
                AuthResult.Authorized(response.body())
            }
            HttpStatusCode.Unauthorized -> {
                AuthResult.Unauthorized()
            }
            else -> {
                AuthResult.UnknownError()
            }
        }
    }

    override suspend fun deleteTimeEntry(id: String, token: String): AuthResult<Boolean> {
        val response = api.deleteTimeEntry(id, token)
        return when (response.status) {
            HttpStatusCode.OK -> {
                AuthResult.Authorized(response.body())
            }
            HttpStatusCode.Unauthorized -> {
                AuthResult.Unauthorized()
            }
            else -> {
                AuthResult.UnknownError()
            }
        }
    }
}