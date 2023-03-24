package de.cgi

import de.cgi.data.datasource.MongoProjectDataSource
import de.cgi.data.datasource.MongoTimeEntryDataSource
import de.cgi.data.datasource.MongoUserDataSource
import io.ktor.server.application.*
import de.cgi.plugins.*
import de.cgi.security.hashing.SHA256HashingService
import de.cgi.security.token.JwtTokenService
import de.cgi.security.token.TokenConfig
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val mongoPw = System.getenv("MONGO_PW")
    val dbName = "timetrackingDB"
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://robbeCoded:$mongoPw@cluster0.mkawkc9.mongodb.net/<$dbName>?retryWrites=true&w=majority"
    ).coroutine
        .getDatabase(dbName)

    val userDataSource = MongoUserDataSource(db)
    val timeEntryDataSource = MongoTimeEntryDataSource(db)
    val projectDataSource = MongoProjectDataSource(db)
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )

    val hashingService = SHA256HashingService()

    configureSecurity(tokenConfig)
    configureMonitoring()
    configureSerialization()
    configureRouting(
        userDataSource = userDataSource,
        tokenConfig = tokenConfig,
        tokenService = tokenService,
        hashingService = hashingService,
        timeEntryDataSource = timeEntryDataSource,
        projectDataSource =projectDataSource
    )
}
