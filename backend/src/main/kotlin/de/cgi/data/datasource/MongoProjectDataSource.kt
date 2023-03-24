package de.cgi.data.datasource

import de.cgi.data.models.Project
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne

class MongoProjectDataSource (
    db: CoroutineDatabase
        ) : ProjectDataSource {

    val projects = db.getCollection<Project>()

    override suspend fun insertProject(project: Project): Boolean {
        return projects.insertOne(project).wasAcknowledged()
    }

    override suspend fun getProjects(): List<Project> {
        return projects.find().toList()
    }

    override suspend fun getProjectById(id: ObjectId): Project? {
        return projects.findOneById(id)
    }

    override suspend fun deleteProject(id: ObjectId): Boolean {
        return projects.deleteOneById(id).wasAcknowledged()
    }
}