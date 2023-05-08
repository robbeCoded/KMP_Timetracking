plugins {
    kotlin("jvm") //version "1.8.20-RC"
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization")
}

group = "de.cgi"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    with(Deps.KMongo){
        implementation(core)
        implementation(coroutine)
    }
    with(Deps.Ktor){
        implementation(serverContentNegotiation)
        implementation(serverNetty)
        implementation(serverCore)
        implementation(serverCoreJvm)
        implementation(authJvm)
        implementation(authJwt)
        implementation(callLogging)
        implementation(logback)
        implementation(serialization)
        implementation(commonsCodec)
        implementation(cors)
    }
    with(Deps.Kotlinx) {
        implementation(dateTime)
    }


}