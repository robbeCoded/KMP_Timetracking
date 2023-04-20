package de.cgi.common.repository

import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProjectNameProviderImpl(
    private val projectRepository: ProjectRepository,
    userRepository: UserRepository,
    private val coroutineScope: CoroutineScope // Inject CoroutineScope
) : ProjectNameProvider {
    private val _projectNameMap =
        MutableStateFlow<ResultState<Map<String, String>?>>(ResultState.Loading)
    private val projectNameMap = _projectNameMap.asStateFlow()

    private val _projectColorMap =
        MutableStateFlow<ResultState<Map<String, String?>?>>(ResultState.Loading)
    private val projectColorMap = _projectColorMap.asStateFlow()

    private val projectUpdates = MutableSharedFlow<Unit>()

    private val userId = userRepository.getUserId()

    init {
        coroutineScope.launch {
            projectUpdates.collect {
                getProjectNameMap()
                getProjectColorMap()
            }
        }
        getProjectNameMap()
        getProjectColorMap()
    }


    override fun notifyProjectUpdates() {
        coroutineScope.launch {
            projectUpdates.emit(Unit)
        }
    }

    override fun getProjectNameMap() {
        projectRepository.getProjects(userId = userId, forceReload = true)
            .onEach { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        val projectMap = resultState.data.associateBy({ it.id }, { it.name })
                        _projectNameMap.value = ResultState.Success(projectMap)
                    }
                    is ResultState.Error -> {
                        _projectNameMap.value = ResultState.Error(resultState.entity)
                    }
                    is ResultState.Loading -> {
                        _projectNameMap.value = ResultState.Loading
                    }
                }
            }
            .launchIn(coroutineScope) // Launch the flow in the provided CoroutineScope
    }
    override fun getProjectColorMap() {
        projectRepository.getProjects(userId = userId, forceReload = true)
            .onEach { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        val projectColorMap = resultState.data.associateBy({ it.id }, { it.color })
                        _projectColorMap.value = ResultState.Success(projectColorMap)
                    }
                    is ResultState.Error -> {
                        _projectColorMap.value = ResultState.Error(resultState.entity)
                    }
                    is ResultState.Loading -> {
                        _projectColorMap.value = ResultState.Loading
                    }
                }
            }
            .launchIn(coroutineScope) // Launch the flow in the provided CoroutineScope
    }

    override fun getProjectNameMapValue(): ResultState<Map<String, String>?> {
        return projectNameMap.value
    }
    override fun getProjectColorMapValue(): ResultState<Map<String, String?>?> {
        return projectColorMap.value
    }

    override fun getProjectNameById(projectId: String?): String? {
        val currentProjectMap = projectNameMap.value
        return if (currentProjectMap is ResultState.Success) {
            currentProjectMap.data?.get(projectId)
        } else {
            null
        }
    }

    override fun getProjectColorById(projectId: String?): String? {
        val currentProjectMap = projectColorMap.value
        return if (currentProjectMap is ResultState.Success) {
            currentProjectMap.data?.get(projectId)
        } else {
            println("getProjectColor else")
            null
        }
    }
}
