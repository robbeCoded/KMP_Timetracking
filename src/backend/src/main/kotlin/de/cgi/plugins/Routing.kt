package de.cgi.plugins

import de.cgi.*
import de.cgi.data.datasource.ProjectDataSource
import de.cgi.data.datasource.TeamDataSource
import de.cgi.data.datasource.TimeEntryDataSource
import de.cgi.data.datasource.UserDataSource
import de.cgi.security.hashing.HashingService
import de.cgi.security.token.TokenConfig
import de.cgi.security.token.TokenService
import io.ktor.server.routing.*
import io.ktor.server.application.*


fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig,
    timeEntryDataSource: TimeEntryDataSource,
    projectDataSource: ProjectDataSource,
    teamDataSource: TeamDataSource
) {
    routing {
        signIn(userDataSource, hashingService, tokenService, tokenConfig)
        signUp(hashingService,userDataSource)

        authenticate()

        newTimeEntry(timeEntryDataSource)
        updateTimeEntry(timeEntryDataSource)
        getTimeEntries(timeEntryDataSource)
        getTimeEntriesForDate(timeEntryDataSource)
        getTimeEntriesForWeek(timeEntryDataSource)
        getTeamTimeEntriesForWeek(timeEntryDataSource)
        getTimeEntry(timeEntryDataSource)
        deleteTimeEntry(timeEntryDataSource)

        newProject(projectDataSource)
        updateProject(projectDataSource)
        getProjectsForUser(projectDataSource)
        getProject(projectDataSource)
        deleteProject(projectDataSource)

        newTeam(teamDataSource)
        updateTeamName(teamDataSource)
        deleteTeam(teamDataSource)
        getTeam(teamDataSource)
        getTeamsForUser(teamDataSource)
        getAllUsers(userDataSource)
        addUsersToTeam(userDataSource)

        getUserRole()

    }
}
