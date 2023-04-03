package de.cgi.android.navigation

sealed class Screen(val route: String) {
    object AuthScreen : Screen("auth_screen")
    object TimeEntryScreen : Screen("timeentry_screen")
    object AddEditTimeEntryScreen: Screen("addedittimeentry_screen")
    object ProjectsScreen: Screen("project_screen")
    object CalenderScreen: Screen("calender_screen")
    object AccountScreen: Screen("account_screen")
    object SettingsScreen: Screen("settings_screen")
}
