package de.cgi.android.navigation.timeentry

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.cgi.android.Router
import de.cgi.android.composableScreen
import de.cgi.android.timetracking.TimeEntryListDestination

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.timeEntryGraph(
    router: Router
) {
    val popupScreens = { route: String? ->
        route == AddEditTimeEntryRoute.route
    }
    navigation(
        route = TimeEntryFeature.route,
        startDestination = TimeEntryListRoute.route,
    ) {
        composableScreen(
            TimeEntryListRoute.route,
            targetIsPopup = popupScreens
        ) {
            TimeEntryListDestination(router = router)
        }
        composableScreen(
            AddEditTimeEntryRoute.route,
            targetIsPopup = popupScreens
        ) {

        }

    }
}
