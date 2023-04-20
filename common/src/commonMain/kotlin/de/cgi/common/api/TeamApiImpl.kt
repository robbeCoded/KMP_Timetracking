package de.cgi.common.api

import de.cgi.common.ErrorEntity
import de.cgi.common.ResultState
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.User
import de.cgi.common.data.model.requests.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TeamApiImpl(
    private val client: HttpClient
) : TeamApi {

    override fun newTeam(team: NewTeamRequest): Flow<ResultState<Team?>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.post(Routes.NEW_TEAM) {
                contentType(ContentType.Application.Json)
                setBody(team)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(response.body()))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose { }
        }
    }

    override fun updateTeamName(request: UpdateTeamNameRequest): Flow<ResultState<Boolean>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.post(Routes.UPDATE_TEAM_NAME) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(true))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose { }
        }
    }

    override fun addManagers(request: AddTeamManagersRequest): Flow<ResultState<Boolean>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.post(Routes.ADD_TEAM_MANAGERS) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(true))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose { }
        }
    }

    override fun removeManager(request: RemoveTeamManagerRequest): Flow<ResultState<Boolean>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.post(Routes.REMOVE_TEAM_MANAGER) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(true))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose { }
        }
    }

    override fun getTeam(id: String): Flow<ResultState<Team?>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.get(Routes.GET_TEAM) {
                parameter("id", id)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(response.body()))
                HttpStatusCode.NotFound -> trySend(ResultState.Success(null))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose { }
        }
    }

    override fun deleteTeam(id: String): Flow<ResultState<Boolean>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.delete(Routes.DELETE_TEAM) {
                parameter("id", id)
            }
            when (response.status) {
                HttpStatusCode.NoContent -> trySend(ResultState.Success(true))
                HttpStatusCode.NotFound -> trySend(ResultState.Success(false))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose { }
        }
    }

    override fun getTeamsForUser(userId: String): Flow<ResultState<List<Team>>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.get(Routes.GET_TEAMS_FOR_USER) {
                parameter("userId", userId)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(response.body()))
                HttpStatusCode.NotFound -> trySend(ResultState.Success(emptyList()))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose { }
        }
    }
    override fun getAllUsers(): Flow<ResultState<List<User>>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.get(Routes.GET_ALL_USERS)
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(response.body()))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose { }
        }
    }
    override fun addUsersToTeam(request: AddUsersToTeamRequest): Flow<ResultState<Boolean>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val response = client.post(Routes.ADD_USERS_TO_TEAM) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            when (response.status) {
                HttpStatusCode.OK -> trySend(ResultState.Success(true))
                else -> trySend(ResultState.Error(ErrorEntity(RuntimeException("Unexpected response status: ${response.status}"))))
            }
            awaitClose { }
        }
    }

}
