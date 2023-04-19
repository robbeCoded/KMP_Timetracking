package de.cgi.android.settings

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
import de.cgi.android.ui.components.MainAppScaffold

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.settingsGraph(
    router: Router
) {
    navigation(
        route = SettingsFeature.route,
        startDestination = SettingsHomeRoute.route,
    ) {

        composable(SettingsHomeRoute.route) {
            MainAppScaffold(
                content = { SettingHomeDestination(router = router) },
                router = router
            )
        }
    }
}