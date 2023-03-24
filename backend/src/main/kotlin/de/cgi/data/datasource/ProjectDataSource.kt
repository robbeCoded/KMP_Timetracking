package de.cgi.data.datasource

import de.cgi.data.models.Project
import org.bson.types.ObjectId

interface ProjectDataSource {
    suspend fun insertProject(project: Project): Boolean
    suspend fun getProjects(): List<Project>
    suspend fun getProjectById(id: ObjectId): Project?
    suspend fun deleteProject(id: ObjectId): Boolean
}