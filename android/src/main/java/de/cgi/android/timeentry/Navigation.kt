package de.cgi.android.timeentry

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.cgi.android.navigation.Router
import de.cgi.android.ui.components.MainAppScaffold

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.timeEntryGraph(
    router: Router
) {
    val popupScreens = { route: String? ->
        route == TimeEntryEditRoute.route
    }
    navigation(
        route = TimeEntryFeature.route,
        startDestination = TimeEntryListRoute.route,
    ) {

        composable(TimeEntryListRoute.route) {
            MainAppScaffold(
                content = { TimeEntryListDestination(router = router) },
                router = router
            )
        }

        composable(
            TimeEntryEditRoute.route,
            arguments = TimeEntryEditRoute.navArguments,
        ) { backStackEntry ->
            MainAppScaffold(content = {
                TimeEntryEditDestination(backStackEntry = backStackEntry, router = router)
            }, router = router)
        }

    }
}
