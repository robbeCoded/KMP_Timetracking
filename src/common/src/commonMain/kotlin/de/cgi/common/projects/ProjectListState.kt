package de.cgi.common.projects

import de.cgi.common.ResultState
import de.cgi.common.data.model.Project
import de.cgi.common.data.model.TimeEntry

data class ProjectListState (
    val projectListState: ResultState<List<Project>> = ResultState.Loading,
    val removeProjectState: ResultState<Unit>? = null
)
