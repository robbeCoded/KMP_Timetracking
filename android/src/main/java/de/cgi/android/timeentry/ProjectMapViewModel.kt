package de.cgi.android.timeentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.android.timeentry.list.TimeEntryListState
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
class ProjectMapViewModel(
    private val useCase: GetProjectsUseCase,
    userRepository: UserRepository
): ViewModel(), ProjectNameProvider {
    private val _projectMap = MutableStateFlow<ResultState<Map<String, String>?>>(ResultState.Loading)
    val projectMap = _projectMap.asStateFlow()

    private val userId = userRepository.getUserId()
    private var loadProjectsJob: Job? = null

    init {
        getProjectMapping()
    }

    private fun getProjectMapping() {
        loadProjectsJob?.cancel()
        loadProjectsJob = useCase.getProjects(userId = userId, forceReload = true)
            .onEach { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        val projectMap = resultState.data.associateBy({ it.id }, { it.name })
                        _projectMap.value = ResultState.Success(projectMap)
                    }
                    is ResultState.Error -> {
                        _projectMap.value = ResultState.Error(resultState.entity)
                    }
                    is ResultState.Loading -> {
                        _projectMap.value = ResultState.Loading
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun getProjectMap(): ResultState<Map<String,String>?> { return projectMap.value}

    override suspend fun getProjectNameById(projectId: String): String? {
        val currentProjectMap = projectMap.value
        return if (currentProjectMap is ResultState.Success) {
            currentProjectMap.data?.get(projectId)
        } else {
            null
        }
    }
}
