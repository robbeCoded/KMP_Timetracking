object Versions {

    const val kotlinCoroutines = "1.6.4"
    const val kotlinxSerialization = "1.4.1"
    const val ktor = "2.2.2"
    const val koinCore = "3.3.2"
    const val koinAndroid = "3.3.2"
    const val koinAndroidCompose = "3.4.1"

    const val kotlinxHtmlJs = "0.7.3"

    const val kmpNativeCoroutinesVersion = "1.0.0-ALPHA-4"

    const val compose = "1.4.0-alpha03"
    const val composeCompiler = "1.4.0"
    const val navCompose = "2.5.2"
    const val accompanist = "0.29.0-alpha"
    const val composeMaterial3 = "1.0.0"

    const val composeDesktopWeb = "1.3.0"

    const val junit = "4.12"
    const val androidXTestJUnit = "1.1.3"
    const val testCore = "1.3.0"
    const val mockito = "3.11.2"
    const val robolectric = "4.6.1"

    const val sqlDelight = "1.5.5"
    const val shadow = "7.0.0"
    const val kotlinterGradle = "3.4.5"

    const val activityCompose = "1.6.0-rc02"
    const val lifecycleKtx = "2.6.0-alpha02"
    const val lifecycleRuntimeKtx = lifecycleKtx
    const val lifecycleViewmodelKtx = lifecycleKtx
    const val osmdroidAndroid = "6.1.10"

    const val slf4j = "1.7.30"
    const val logback = "1.2.3"
    const val kermit = "1.0.0"
    const val commonsCodec = "1.15"
    const val gradleVersionsPlugin = "0.39.0"

    const val kmongo = "4.5.1"

    const val androidNavigation = "1.7.36-beta"
    const val icons = "1.0.0"
}

object AndroidSdk {
    const val min = 21
    const val compile = 33
    const val target = compile
}


object Deps {
    object Gradle {
        const val kotlinter = "org.jmailen.gradle:kotlinter-gradle:${Versions.kotlinterGradle}"
        const val shadow =
            "gradle.plugin.com.github.jengelman.gradle.plugins:shadow:${Versions.shadow}"
        const val gradleVersionsPlugin =
            "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersionsPlugin}"
        const val sqlDelight = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
    }

    object Kotlinx {
        const val serializationCore =
            "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}"
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
        const val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"
        const val htmlJs = "org.jetbrains.kotlinx:kotlinx-html-js:${Versions.kotlinxHtmlJs}"
        const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"
    }

    object Android {
        const val osmdroidAndroid = "org.osmdroid:osmdroid-android:${Versions.osmdroidAndroid}"
    }

    object AndroidX {
        const val benchmarkMacroJunit4 = "androidx.benchmark:benchmark-macro-junit4:1.1.0-rc01"
        const val benchmarkJunit4 = "androidx.benchmark:benchmark-junit4:1.1.0-rc01"
        const val lifecycleRuntimeCompose =
            "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycleRuntimeKtx}"
        const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
        const val lifecycleViewmodelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodelKtx}"
        const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
        const val metrics = "androidx.metrics:metrics-performance:1.0.0-alpha01"
        const val testEspressoCore = "androidx.test.espresso:espresso-core:3.4.0"
        const val testExtJunit = "androidx.test.ext:junit:1.1.3"
        const val testUiautomator = "androidx.test.uiautomator:uiautomator:2.2.0"

        const val material3 = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
        const val material3WindowSizeClass =
            "androidx.compose.material3:material3-window-size-class:${Versions.composeMaterial3}"
        const val splashScreen = "androidx.core:core-splashscreen:1.0.0"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val androidXTestJUnit = "androidx.test.ext:junit:${Versions.androidXTestJUnit}"
        const val mockito = "org.mockito:mockito-inline:${Versions.mockito}"
        const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
        const val testCore = "androidx.test:core:${Versions.testCore}"

        const val composeUiTest = "androidx.compose.ui:ui-test:${Versions.compose}"
        const val composeUiTestJUnit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
        const val composeNavTesting =
            "androidx.navigation:navigation-testing:${Versions.navCompose}"
    }

    object Compose {
        const val compiler = "androidx.compose.compiler:compiler:${Versions.composeCompiler}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val foundationLayout =
            "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.navCompose}"

        const val coilCompose = "io.coil-kt:coil-compose:2.0.0"

        const val composeImageLoader = "io.github.qdsfdhvh:image-loader:1.2.9"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koinCore}"
        const val test = "io.insert-koin:koin-test:${Versions.koinCore}"
        const val testJUnit4 = "io.insert-koin:koin-test-junit4:${Versions.koinCore}"
        const val android = "io.insert-koin:koin-android:${Versions.koinAndroid}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koinAndroidCompose}"
    }

    object Ktor {
        const val serverCore = "io.ktor:ktor-server-core:${Versions.ktor}"
        const val serverNetty = "io.ktor:ktor-server-netty:${Versions.ktor}"
        const val serverCors = "io.ktor:ktor-server-cors:${Versions.ktor}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val json = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"

        const val serverCoreJvm = "io.ktor:ktor-server-core-jvm:${Versions.ktor}"
        const val authJvm = "io.ktor:ktor-server-auth-jvm:${Versions.ktor}"
        const val authJwt = "io.ktor:ktor-server-auth-jwt-jvm:${Versions.ktor}"
        const val callLogging = "io.ktor:ktor-server-call-logging-jvm:${Versions.ktor}"
        const val logback = "ch.qos.logback:logback-classic:${Versions.logback}"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json-jvm:${Versions.ktor}"
        const val commonsCodec = "commons-codec:commons-codec:${Versions.commonsCodec}"
        const val serverContentNegotiation = "io.ktor:ktor-server-content-negotiation:${Versions.ktor}"

        const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val clientJson = "io.ktor:ktor-client-json:${Versions.ktor}"
        const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        const val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val clientJava = "io.ktor:ktor-client-java:${Versions.ktor}"
        const val clientDarwin = "io.ktor:ktor-client-darwin:${Versions.ktor}"
        const val clientJs = "io.ktor:ktor-client-js:${Versions.ktor}"
        const val features = "io.ktor:ktor-client-features:${Versions.ktor}"

        const val cors = "io.ktor:ktor-server-cors:${Versions.ktor}"

    }

    object KMongo {
        const val core = "org.litote.kmongo:kmongo:${Versions.kmongo}"
        const val coroutine = "org.litote.kmongo:kmongo-coroutine:${Versions.kmongo}"
    }

    object Icons {
        const val android = "br.com.devsrsouza.compose.icons.android:feather:${Versions.icons}"
    }

    object androidNavigation {
        const val core = "io.github.raamcosta.compose-destinations:core:${Versions.androidNavigation}"
        const val ksp = "io.github.raamcosta.compose-destinations:ksp:${Versions.androidNavigation}"
    }

    object sqlDelight {
        const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        const val android = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
    }
}
