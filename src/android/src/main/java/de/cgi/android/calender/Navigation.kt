package de.cgi.android.calender

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
fun NavGraphBuilder.calenderGraph(
    router: Router
) {
    navigation(
        route = CalenderFeature.route,
        startDestination = CalenderScreenRoute.route,
    ) {

        composable(CalenderScreenRoute.route) {
            MainAppScaffold(
                content = { CalenderDestination(router = router) },
                router = router
            )
        }
    }
}