import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
    id ("com.google.devtools.ksp")
    id("com.rickclephas.kmp.nativecoroutines")
}

android {
    compileSdk = AndroidSdk.compile
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    namespace = "de.cgi.common"
}

kotlin {

    val sdkName: String? = System.getenv("SDK_NAME")
    android()
    js(IR) {
        useCommonJs()
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                with(Deps.Ktor) {
                    implementation(clientCore)
                    implementation(clientJson)
                    implementation(clientLogging)
                    implementation(contentNegotiation)
                    implementation(json)
                }

                with(Deps.Kotlinx) {
                    implementation(coroutinesCore)
                    implementation(serializationCore)
                    implementation(dateTime)
                }
                with(Deps.Koin) {
                    implementation(core)
                }
                implementation ("com.google.code.gson:gson:2.8.9")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Deps.Kotlinx.coroutinesTest)
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val androidMain by getting {
            dependencies {
                with(Deps.Ktor){
                    implementation(clientAndroid)
                    implementation(json)
                    implementation(clientSerialization)
                }
                with(Deps.AndroidX){
                    implementation(lifecycleViewmodelKtx)
                }
                with(Deps.Koin){
                    implementation(android)
                }

            }
        }

        val jsMain by getting {
            dependencies {
                implementation(Deps.Ktor.clientJs)
            }
        }

    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}
