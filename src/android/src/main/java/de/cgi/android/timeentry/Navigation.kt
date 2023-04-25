package de.cgi.android.timeentry

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.cgi.android.navigation.Router
import de.cgi.android.timeentry.addedit.TimeEntryAddDestination
import de.cgi.android.timeentry.addedit.TimeEntryEditDestination
import de.cgi.android.timeentry.list.TimeEntryListDestination
import de.cgi.android.ui.components.MainAppScaffold

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.timeEntryGraph(
    router: Router
) {
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

        composable(TimeEntryAddRoute.route) {
            MainAppScaffold(
                content = { TimeEntryAddDestination(router = router) },
                router = router
            )
        }

    }
}
