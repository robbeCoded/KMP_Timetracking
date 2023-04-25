package de.cgi.android.projects.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.UserRepository
import de.cgi.common.data.model.Project
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProjectListViewModel(
    private val projectListUseCase: ProjectListUseCase,
    userRepository: UserRepository,
) : ViewModel() {

    private val userId = userRepository.getUserId()

    private var loadProjectsJob: Job? = null
    private var deleteProjectJob: Job? = null

    private val _listState = MutableStateFlow(ProjectListState())
    val listState =  _listState.asStateFlow()

    val updateTrigger = mutableStateOf(false)

    init {
        getProjects()
    }
    fun notifyProjectUpdates() {
        updateTrigger.value = !updateTrigger.value
    }


    fun getProjects() {
        loadProjectsJob?.cancel()
        loadProjectsJob = projectListUseCase.getProjects(userId = userId, forceReload = true).onEach { resultState ->
            _listState.update { it.copy(projectListState = resultState) }
        }.launchIn(viewModelScope)
    }

    fun deleteProject(project: Project) {
        deleteProjectJob?.cancel()
        deleteProjectJob = projectListUseCase.deleteProject(project.id).onEach {
            _listState.update {
                it.copy(removeProjectState = null)
            }
        }.launchIn(viewModelScope)
    }

}