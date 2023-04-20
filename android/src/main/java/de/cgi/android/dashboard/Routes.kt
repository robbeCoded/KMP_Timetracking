package de.cgi.android.dashboard

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import de.cgi.android.navigation.AppRoutes

object DashboardFeature : AppRoutes("DashboardFeature")

// Screens
object DashboardScreenRoute : AppRoutes("Dashboard")

object TeamDashboardScreenRoute : AppRoutes("TeamDashboard")

object TeamListScreenRoute : AppRoutes("TeamList")

object TeamAddRoute : AppRoutes("TeamAdd")
object TeamEditRoute : AppRoutes("TeamEdit") {
    private const val paramTeamById = "teamId"
    override val route: String = "$baseRoute/{$paramTeamById}"
    val navArguments = listOf(
        navArgument(paramTeamById) {
            type = NavType.StringType
        }
    )

    fun getTimeEntryId(backStackEntry: NavBackStackEntry): String? =
        backStackEntry.arguments?.getString(paramTeamById)

    fun buildEditTeamRoute(id: String) = "$baseRoute/$id"
}