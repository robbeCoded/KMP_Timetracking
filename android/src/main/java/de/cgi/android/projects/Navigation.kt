package de.cgi.android.projects

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.cgi.android.navigation.Router
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

    }
}