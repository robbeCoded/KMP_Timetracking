import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("multiplatform")
    id ("kotlinx-serialization")
    id("com.squareup.sqldelight")
    id ("com.android.library")
    id ("com.google.devtools.ksp")
    id ("com.rickclephas.kmp.nativecoroutines")
    id ("de.comahe.i18n4k") version "0.5.0"
}

i18n4k {
    sourceCodeLocales = listOf("en", "de")
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
        browser() {
            commonWebpackConfig {

            }
        }
        binaries.executable()
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
                    implementation(clientAuth)
                }

                with(Deps.Kotlinx) {
                    implementation(coroutinesCore)
                    implementation(serializationCore)
                    implementation(dateTime)
                }
                with(Deps.Koin) {
                    implementation(core)
                }
                with(Deps.sqlDelight){
                    implementation(runtime)
                }
                //internationalisation
                implementation("de.comahe.i18n4k:i18n4k-core:0.5.0")

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
                with(Deps.sqlDelight){
                    implementation(android)
                }

            }
        }

        val jsMain by getting {
            dependencies {
                implementation(Deps.Ktor.clientJs)
                implementation("app.cash.sqldelight:sqljs-driver:2.0.0-alpha05")
                implementation(npm("sql.js", "1.6.2"))
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            }
        }

    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "de.cgi.shared.cache"
    }
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}
