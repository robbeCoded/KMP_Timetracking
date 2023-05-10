package de.cgi.common.projects

import de.cgi.common.util.ResultState
import de.cgi.common.data.model.Project

data class ProjectListState (
    val projectListState: ResultState<List<Project>> = ResultState.Loading,
    val removeProjectState: ResultState<Unit>? = null
)
