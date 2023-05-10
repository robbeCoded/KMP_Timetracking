package de.cgi.common.repository

import de.cgi.common.util.ResultState

interface ProjectMapProvider {
    fun notifyProjectUpdates()
    suspend fun getProjectNameMapUser()
    suspend fun getProjectColorMapUser()
    fun getProjectNameMapValue(): ResultState<Map<String, String>?>
    fun getProjectColorMapValue(): ResultState<Map<String, String?>?>
    fun getProjectNameById(projectId: String?): String?
    fun getProjectColorById(projectId: String?): String?
    suspend fun getProjectNameMapForUserList(userIds: List<String>): ResultState<Map<String, String>?>
}
