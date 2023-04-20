package de.cgi.common.repository

import de.cgi.common.ResultState
import kotlinx.coroutines.flow.Flow

interface ProjectNameProvider {

    fun getProjectNameMapValue(): ResultState<Map<String, String>?>
    fun getProjectNameMap()
    fun getProjectNameById(projectId: String?): String?

    fun getProjectColorMapValue(): ResultState<Map<String, String?>?>
    fun getProjectColorMap()
    fun getProjectColorById(projectId: String?): String?

    fun notifyProjectUpdates()
}
