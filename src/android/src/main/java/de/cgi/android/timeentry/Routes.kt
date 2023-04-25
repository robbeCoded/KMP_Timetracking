package de.cgi.android.timeentry

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import de.cgi.android.navigation.AppRoutes

object TimeEntryFeature : AppRoutes("TimeEntryFeature")

// Screens
object TimeEntryListRoute : AppRoutes("TimeEntryList")

object TimeEntryEditRoute : AppRoutes("TimeEntryEdit") {
    private const val paramTimeEntryId = "timeEntryId"
    override val route: String = "$baseRoute/{$paramTimeEntryId}"
    val navArguments = listOf(
        navArgument(paramTimeEntryId) {
            type = NavType.StringType
        }
    )

    fun getTimeEntryId(backStackEntry: NavBackStackEntry): String? =
        backStackEntry.arguments?.getString(paramTimeEntryId)

    fun buildAddEditTimeEntryRoute(id: String) = "$baseRoute/$id"
}

object TimeEntryAddRoute : AppRoutes("TimeEntryAdd")