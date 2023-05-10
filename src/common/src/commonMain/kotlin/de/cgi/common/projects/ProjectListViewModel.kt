package de.cgi.common.projects

import de.cgi.common.UserRepository
import de.cgi.common.data.model.Project
import kotlinx.coroutines.flow.StateFlow

expect class ProjectListViewModel(
    projectListUseCase: ProjectListUseCase,
    userRepository: UserRepository
) {
    val listState: StateFlow<ProjectListState>

    fun notifyProjectUpdates()
    fun getProjects()
    fun deleteProject(project: Project)
}
