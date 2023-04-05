package de.cgi.android

import de.cgi.common.data.model.TimeEntry

interface Router {
    fun showAuth()

    fun showTimeEntryList()
    fun showTimeEntryDetails(timeEntry: TimeEntry? = null)

    fun showProjectList()
    fun showProjectDetails(id: String? = null)

    fun showSettings()
    fun back()
    fun navigationUp(): Boolean
}