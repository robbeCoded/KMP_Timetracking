package de.cgi.data.datasource

import de.cgi.data.models.Project
import de.cgi.data.models.TimeEntry
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.litote.kmongo.setTo

class MongoProjectDataSource (
    db: CoroutineDatabase
        ) : ProjectDataSource {

    private val projects = db.getCollection<Project>()

    override suspend fun insertProject(project: Project): Boolean {
        return projects.insertOne(project).wasAcknowledged()
    }

    override suspend fun updateProject(project: Project): Boolean {
        val filter = Project::id eq project.id
        val update = set(
            Project::name.setTo(project.name),
            Project::startDate.setTo(project.startDate),
            Project::endDate.setTo(project.endDate),
            Project::description.setTo(project.description),
            Project::userId.setTo(project.userId),
            Project::color.setTo(project.color)
        )
        val updateResult = projects.updateOne(filter, update)
        return updateResult.wasAcknowledged()
    }

    override suspend fun getProjects(userId: ObjectId): List<Project> {
        return projects.find(TimeEntry::userId eq userId).toList()
    }

    override suspend fun getProjectById(id: ObjectId): Project? {
        return projects.findOneById(id)
    }

    override suspend fun deleteProject(id: ObjectId): Boolean {
        return projects.deleteOneById(id).wasAcknowledged()
    }
}