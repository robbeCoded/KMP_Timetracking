package de.cgi.android.projects

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.cgi.android.navigation.Router
import de.cgi.android.timeentry.TimeEntryAddDestination
import de.cgi.android.timeentry.TimeEntryAddRoute
import de.cgi.android.timeentry.TimeEntryEditDestination
import de.cgi.android.timeentry.TimeEntryEditRoute
import de.cgi.android.ui.components.MainAppScaffold

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.projectsGraph(
    router: Router
) {
    navigation(
        route = ProjectsFeature.route,
        startDestination = ProjectListRoute.route,
    ) {

        composable(ProjectListRoute.route) {
            MainAppScaffold(
                content = { ProjectListDestination(router = router) },
                router = router
            )
        }
        composable(
            ProjectEditRoute.route,
            arguments = ProjectEditRoute.navArguments,
        ) { backStackEntry ->
            MainAppScaffold(content = {
                ProjectEditDestination(backStackEntry = backStackEntry, router = router)
            }, router = router)
        }

        composable(ProjectAddRoute.route) {
            MainAppScaffold(
                content = { ProjectAddDestination(router = router) },
                router = router
            )
        }

    }
}