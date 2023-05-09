plugins {
    id("com.android.application")
    kotlin("android")
    id ("com.github.ben-manes.versions")
    id ("kotlin-kapt")
}


android {
    compileSdk = AndroidSdk.compile

    defaultConfig {
        applicationId = "de.cgi.myapplication"
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target

        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
    namespace = "de.cgi"

    testOptions {
        managedDevices {
            devices {
                create<com.android.build.api.dsl.ManagedVirtualDevice>("pixel5api32") {
                    device = "Pixel 5"
                    apiLevel = 32
                    systemImageSource = "google"
                }
            }
        }
    }
    applicationVariants.all {
        addJavaSourceFoldersToModel(
            File(buildDir, "generated/ksp/$name/kotlin")
        )
    }
}



dependencies {
    with(Deps.Android) {
        implementation(osmdroidAndroid)
    }

    with(Deps.AndroidX) {
        implementation(lifecycleRuntimeCompose)
        implementation(lifecycleRuntimeKtx)
        implementation(lifecycleViewmodelKtx)
        implementation(activityCompose)
        implementation(material3)
        implementation(material3WindowSizeClass)
        implementation(splashScreen)
    }

    with(Deps.Compose) {
        implementation(compiler)
        implementation(ui)
        implementation(uiGraphics)
        implementation(foundationLayout)
        implementation(material)
        implementation(navigation)
        implementation(coilCompose)
        implementation(uiTooling)
    }

    with(Deps.Test) {
        testImplementation(junit)
        androidTestImplementation(androidXTestJUnit)
        testImplementation(testCore)
        testImplementation(robolectric)
        testImplementation(mockito)

        // Compose testing dependencies
        androidTestImplementation(composeUiTest)
        androidTestImplementation(composeUiTestJUnit)
        androidTestImplementation(composeNavTesting)
        debugImplementation(composeUiTestManifest)
    }
    with(Deps.Kotlinx) {
        implementation(dateTime)
    }

    with(Deps.Kodein){
        implementation(compose)
        implementation(core)
        implementation(androidX)
        implementation(androidXViewModel)
    }
    with(Deps.Ktor){
        implementation(clientAndroid)
    }

    with(Deps.Icons) {
        implementation(android)
    }
    implementation("io.github.bytebeats:compose-charts:0.1.2")

    implementation ("org.slf4j:slf4j-api:1.7.36") // Use the latest version available
    implementation ("com.github.tony19:logback-android:2.0.0") // Use the latest version available

    implementation(project(":common"))
}

