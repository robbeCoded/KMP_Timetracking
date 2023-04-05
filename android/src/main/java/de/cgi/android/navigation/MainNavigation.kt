package de.cgi.android.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import de.cgi.android.AppState
import de.cgi.android.navigation.auth.AuthFeature
import de.cgi.android.navigation.auth.authGraph
import de.cgi.android.navigation.timeentry.timeEntryGraph

@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainNavigation(
    appState: AppState,
) {
    val initialRoute = AuthFeature.route

    AnimatedNavHost(
        appState.navHostController,
        startDestination = initialRoute
    ) {
        authGraph(appState.router)
        timeEntryGraph(appState.router)
    }
}