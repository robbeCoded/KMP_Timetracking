package de.cgi.android.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import de.cgi.android.auth.AuthScreen
import de.cgi.android.TimeEntryScreen
import de.cgi.android.projects.ProjectsScreen
import de.cgi.android.timetracking.addedittimeentry.AddEditTimeEntryScreen
import de.cgi.common.data.model.TimeEntry

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.AuthScreen.route ) {
        composable(route = Screen.AuthScreen.route) {
            AuthScreen(navController = navController)
        }
        composable(route = Screen.TimeEntryScreen.route) {
            TimeEntryScreen(navController = navController)
        }
        composable(route = Screen.TimeEntryScreen.route) {
            ProjectsScreen(navController = navController)
        }
        composable(route = Screen.TimeEntryScreen.route) {
            TimeEntryScreen(navController = navController)
        }
        composable(route = Screen.AddEditTimeEntryScreen.route + "?timeentry_id={timeentry_id}",
        arguments = listOf(
            navArgument("timeentry_id") {
                type = NavType.StringType
                defaultValue = null
                nullable = true
            }
        )) {
            entry -> AddEditTimeEntryScreen(
            timeEntryId = entry.arguments?.getString("timeentry_id"),
            navController = navController
        )
        }
    }

}