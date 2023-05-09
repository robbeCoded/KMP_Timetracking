package de.cgi.android.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import de.cgi.android.AppState
import de.cgi.android.account.accountGraph
import de.cgi.android.auth.AuthFeature
import de.cgi.android.auth.authGraph
import de.cgi.android.calender.calenderGraph
import de.cgi.android.dashboard.dashboardGraph
import de.cgi.android.projects.projectsGraph
import de.cgi.android.settings.settingsGraph
import de.cgi.android.timeentry.timeEntryGraph

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainNavigation(
    appState: AppState,
) {
    val initialRoute = AuthFeature.route

    NavHost(
        appState.navHostController,
        startDestination = initialRoute
    ) {
        authGraph(appState.router)
        timeEntryGraph(appState.router)
        projectsGraph(appState.router)
        dashboardGraph(appState.router)
        accountGraph(appState.router)
        settingsGraph(appState.router)
        calenderGraph(appState.router)
    }
}