package de.cgi.common.repository

import de.cgi.common.ErrorEntity
import de.cgi.common.ResultState
import de.cgi.common.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProjectMapProviderImpl(
    private val projectRepository: ProjectRepository,
    userRepository: UserRepository,
    private val coroutineScope: CoroutineScope
) : ProjectMapProvider {
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
                getProjectNameMapUser()
                getProjectColorMapUser()
            }
        }
    }


    override fun notifyProjectUpdates() {
        coroutineScope.launch {
            projectUpdates.emit(Unit)
        }
    }

    override suspend fun getProjectNameMapUser() {
        projectRepository.getProjectsForUser(userId = userId, forceReload = true)
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
            .launchIn(coroutineScope)
    }

    override suspend fun getProjectColorMapUser() {
        projectRepository.getProjectsForUser(userId = userId, forceReload = true)
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
            .launchIn(coroutineScope)
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

    override suspend fun getProjectNameMapForUserList(userIds: List<String>): ResultState<Map<String, String>?> {
        val projectMaps = mutableListOf<Map<String, String>>()

        for (userId in userIds) {
            val result = projectRepository.getProjectsForUser(userId = userId, forceReload = true)
                .firstOrNull()

            when (result) {
                is ResultState.Success -> {
                    val projectMap = result.data.associateBy({ it.id }, { it.name })
                    projectMaps.add(projectMap)
                }
                is ResultState.Error -> {
                    return ResultState.Error(result.entity)
                }
                is ResultState.Loading -> {
                    // Loading state can be ignored in this context as we want to process all results at once
                }
                else -> return ResultState.Error(ErrorEntity())
            }
        }

        return ResultState.Success(projectMaps.flattenEntries())
    }



    private fun <K, V> List<Map<K, V>>.flattenEntries(): Map<K, V> {
        val result = mutableMapOf<K, V>()
        for (map in this) {
            result.putAll(map)
        }
        return result
    }
}
