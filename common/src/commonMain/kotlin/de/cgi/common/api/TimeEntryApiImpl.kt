package de.cgi.common.api

import de.cgi.common.ErrorEntity
import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.data.model.requests.NewTimeEntry
import de.cgi.common.data.model.requests.TimeEntryRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TimeEntryApiImpl(private val client: HttpClient) : TimeEntryApi {

    override fun newTimeEntry(timeEntry: NewTimeEntry): Flow<ResultState<TimeEntry?>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.post(routes.NEW_TIME_ENTRY) {
                contentType(ContentType.Application.Json)
                setBody(timeEntry)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(response.body()))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose {  }
        }
    }

    override fun getTimeEntries(): Flow<ResultState<List<TimeEntry>>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.get(routes.GET_TIME_ENTRIES)
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(response.body()))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.bodyAsText()}"))))
            }
            awaitClose {  }
        }
    }

    override fun getTimeEntryById(timeEntryRequest: TimeEntryRequest): Flow<ResultState<TimeEntry?>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.get(routes.GET_TIME_ENTRY_BY_ID) {
                parameter("id", timeEntryRequest.id)
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

    override fun deleteTimeEntry(timeEntryRequest: TimeEntryRequest): Flow<ResultState<Boolean>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.delete(routes.DELETE_TIME_ENTRY) {
                parameter("id", timeEntryRequest.id)
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