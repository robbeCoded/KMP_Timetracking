package de.cgi.android

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.cgi.android.navigation.Router
import de.cgi.android.navigation.RouterImpl


@ExperimentalAnimationApi
@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): AppState {
    return remember(navController) {
        AppState(RouterImpl(navController), navController)
    }
}

@Stable
data class AppState(
    val router: Router,
    internal val navHostController: NavHostController,
)