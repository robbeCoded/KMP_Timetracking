package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProjectNameProviderImpl(
    private val projectRepository: ProjectRepository,
    userRepository: UserRepository,
    private val coroutineScope: CoroutineScope // Inject CoroutineScope
) : ProjectNameProvider {
    private val _projectMap =
        MutableStateFlow<ResultState<Map<String, String>?>>(ResultState.Loading)
    private val projectMap = _projectMap.asStateFlow()

    private val userId = userRepository.getUserId()

    init {
        getProjectMapping()
    }

    override fun getProjectMapping() {
        projectRepository.getProjects(userId = userId, forceReload = true)
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
            }
            .launchIn(coroutineScope) // Launch the flow in the provided CoroutineScope
    }

    override fun getProjectMap(): ResultState<Map<String, String>?> {
        return projectMap.value
    }

    override fun getProjectNameById(projectId: String?): String? {
        val currentProjectMap = projectMap.value
        return if (currentProjectMap is ResultState.Success) {
            currentProjectMap.data?.get(projectId)
        } else {
            null
        }
    }
}
