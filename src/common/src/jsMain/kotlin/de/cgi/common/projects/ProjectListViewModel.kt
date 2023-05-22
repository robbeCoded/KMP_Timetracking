package de.cgi.common.projects

import androidx.compose.runtime.mutableStateOf
import de.cgi.common.UserRepository
import de.cgi.common.data.model.Project
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*

actual class ProjectListViewModel actual constructor(
    private val projectListUseCase: ProjectListUseCase,
    userRepository: UserRepository
) {
    private val userId = userRepository.getUserId()

    private var loadProjectsJob: Job? = null
    private var deleteProjectJob: Job? = null

    private val _listState = MutableStateFlow(ProjectListState())
    actual val listState =  _listState.asStateFlow()

    val updateTrigger = mutableStateOf(false)

    val jsScope = MainScope()

    init {
        getProjects()
    }

    actual fun notifyProjectUpdates() {
        _listState.value = ProjectListState()
        updateTrigger.value = !updateTrigger.value
    }

    actual fun getProjects() {
        loadProjectsJob?.cancel()
        loadProjectsJob = projectListUseCase.getProjects(userId = userId, forceReload = true).onEach { resultState ->
            _listState.update { it.copy(projectListState = resultState) }
        }.launchIn(jsScope)
    }

    actual fun deleteProject(project: Project) {
        deleteProjectJob?.cancel()
        deleteProjectJob = projectListUseCase.deleteProject(project.id).onEach {
            _listState.update {
                it.copy(removeProjectState = null)
            }
        }.launchIn(jsScope)
    }

}