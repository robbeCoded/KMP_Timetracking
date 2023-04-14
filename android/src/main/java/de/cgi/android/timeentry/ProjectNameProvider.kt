package de.cgi.android.timeentry

interface ProjectNameProvider {
    suspend fun getProjectNameById(projectId: String): String?
}
