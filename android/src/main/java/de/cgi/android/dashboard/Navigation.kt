package de.cgi.android.dashboard

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.cgi.android.dashboard.team.TeamDashboardDestination
import de.cgi.android.navigation.Router
import de.cgi.android.ui.components.MainAppScaffold

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.dashboardGraph(
    router: Router
) {
    navigation(
        route = DashboardFeature.route,
        startDestination = DashboardScreenRoute.route,
    ) {

        composable(DashboardScreenRoute.route) {
            MainAppScaffold(
                content = { DashboardScreenDestination(router = router) },
                router = router
            )
        }

        composable(TeamDashboardScreenRoute.route) {
            MainAppScaffold(
                content = { TeamDashboardDestination(router = router) },
                router = router
            )
        }

    }
}