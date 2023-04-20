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
        getTimeEntry(timeEntryDataSource)
        deleteTimeEntry(timeEntryDataSource)

        newProject(projectDataSource)
        updateProject(projectDataSource)
        getProjects(projectDataSource)
        getProject(projectDataSource)
        deleteProject(projectDataSource)

        newTeam(teamDataSource)
        updateTeamName(teamDataSource)
        addTeamManagers(teamDataSource)
        removeTeamManager(teamDataSource)
        deleteTeam(teamDataSource)
        getTeam(teamDataSource)
        getTeamsForUser(teamDataSource)
        getAllUsers(userDataSource)
        addUsersToTeam(userDataSource)

        getUserRole()

    }
}
