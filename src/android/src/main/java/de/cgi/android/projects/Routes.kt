package de.cgi.android.projects

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import de.cgi.android.navigation.AppRoutes

object ProjectsFeature : AppRoutes("ProjectsFeature")

// Screens
object ProjectListRoute : AppRoutes("ProjectList")

object ProjectEditRoute : AppRoutes("ProjectEdit") {
    private const val paramProjectId = "projectId"
    override val route: String = "$baseRoute/{$paramProjectId}"
    val navArguments = listOf(
        navArgument(paramProjectId) {
            type = NavType.StringType
        }
    )

    fun getProjectById(backStackEntry: NavBackStackEntry): String? =
        backStackEntry.arguments?.getString(paramProjectId)

    fun buildAddEditProjectRoute(id: String) = "$baseRoute/$id"
}

object ProjectAddRoute : AppRoutes("ProjectAdd")