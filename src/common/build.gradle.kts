import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("multiplatform")
    id ("kotlinx-serialization")
    id ("com.android.library")
    id ("com.google.devtools.ksp")
    id ("com.rickclephas.kmp.nativecoroutines")
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
    android()
    js(IR) {
        useCommonJs()
        browser {
            commonWebpackConfig {
            }
        }
        binaries.executable()
    }
    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting {
            dependencies {
                with(Deps.Ktor) {
                    implementation(clientCore)
                    implementation(clientJson)
                    implementation(clientLogging)
                    implementation(contentNegotiation)
                    implementation(json)
                    implementation(clientAuth)
                }

                with(Deps.Kotlinx) {
                    implementation(coroutinesCore)
                    implementation(serializationCore)
                    implementation(dateTime)
                }

                with(Deps.Kodein){
                    implementation(compose)
                    implementation(core)
                }
                with(Deps.Compose){
                    implementation(runtime)
                }
                implementation("com.russhwolf:multiplatform-settings:1.0.0")
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
                    implementation(lifecycleRuntimeCompose)
                    implementation(lifecycleRuntimeKtx)
                    implementation(lifecycleViewmodelKtx)
                }

                with(Deps.Kodein){
                    implementation(androidX)
                    implementation(androidXViewModel)
                }

                implementation(Deps.Kotlinx.coroutinesAndroid)
                /*
                with(Deps.sqlDelight){
                    implementation(android)
                }*/

            }
        }

        val jsMain by getting {
            dependencies {
                implementation(Deps.Compose.runtime)
                implementation(Deps.Ktor.clientJs)
                implementation(Deps.Kotlinx.coroutinesJS)

                //implementation("app.cash.sqldelight:sqljs-driver:2.0.0-alpha05")
                //implementation(npm("sql.js", "1.6.2"))
                //implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            }
        }

    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
/*
sqldelight {
    database("AppDatabase") {
        packageName = "de.cgi.shared.cache"
    }
}*/

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}
