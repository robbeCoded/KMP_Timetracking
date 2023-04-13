package de.cgi.android.navigation

import de.cgi.common.data.model.Project
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

    fun showSettings()
    fun back()
    fun navigationUp(): Boolean
}