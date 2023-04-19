package de.cgi.common.repository

import de.cgi.common.ResultState
import kotlinx.coroutines.flow.Flow

interface ProjectNameProvider {

    fun getProjectMap(): ResultState<Map<String, String>?>
    fun getProjectMapping()
    fun getProjectNameById(projectId: String?): String?
}
