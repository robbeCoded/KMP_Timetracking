package de.cgi.common.projects

import de.cgi.common.UserRepository
import de.cgi.common.data.model.Project
import kotlinx.coroutines.flow.StateFlow

actual class ProjectListViewModel actual constructor(
    projectListUseCase: ProjectListUseCase,
    userRepository: UserRepository
) {
    actual val listState: StateFlow<ProjectListState>
        get() = TODO("Not yet implemented")

    actual fun notifyProjectUpdates() {
    }

    actual fun getProjects() {
    }

    actual fun deleteProject(project: Project) {
    }

}