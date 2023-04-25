package de.cgi.android.navigation

import de.cgi.common.data.model.Project
import de.cgi.common.data.model.Team
import de.cgi.common.data.model.TimeEntry

interface Router {
    fun showSignIn()

    fun showSignUp()

    fun showTimeEntryList()
    fun showTimeEntryEdit(timeEntry: TimeEntry)
    fun showTimeEntryAdd()

    fun showProjectList()
    fun showProjectEdit(project: Project)
    fun showProjectAdd()

    fun showDashboard()
    fun showTeamDashboard()

    fun showTeamList()
    fun showTeamEdit(team: Team)
    fun showTeamAdd()

    fun showCalender()

    fun showAccount()
    fun showSettings()
    fun back()
    fun backFromAddEdit(update: () -> Unit)
    fun navigationUp(): Boolean

    fun getCurrentRoute(): String?
}