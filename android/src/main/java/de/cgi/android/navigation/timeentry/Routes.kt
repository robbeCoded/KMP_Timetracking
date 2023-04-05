package de.cgi.android.navigation.timeentry

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import de.cgi.android.navigation.AppRoutes

object TimeEntryFeature : AppRoutes("TimeEntryFeature")

// Screens
object TimeEntryListRoute : AppRoutes("TimeEntryList")

object AddEditTimeEntryRoute : AppRoutes("AddEditTimeEntryRoute") {
    private const val paramTimeEntryId = "timeentryId"
    override val route: String = "$baseRoute/{$paramTimeEntryId}"
    val navArguments = listOf(
        navArgument(paramTimeEntryId) {
            type = NavType.StringType
        }
    )

    fun getTimeEntryId(backStackEntry: NavBackStackEntry): String? =
        backStackEntry.arguments?.getString(paramTimeEntryId)

    fun buildAddEditTimeEntryRoute(id: String?) = id?.let { "$baseRoute?$paramTimeEntryId=$id" } ?: baseRoute
}