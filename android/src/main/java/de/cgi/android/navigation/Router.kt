package de.cgi.android.navigation

import de.cgi.common.data.model.TimeEntry

interface Router {
    fun showAuth()

    fun showTimeEntryList()
    fun showTimeEntryEdit(timeEntry: TimeEntry)

    fun showTimeEntryAdd()
    fun showProjectList()
    fun showProjectDetails(id: String? = null)

    fun showSettings()
    fun back()
    fun navigationUp(): Boolean
}