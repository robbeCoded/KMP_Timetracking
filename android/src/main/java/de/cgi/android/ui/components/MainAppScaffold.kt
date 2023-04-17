package de.cgi.android.ui.components

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import de.cgi.android.navigation.Router
import de.cgi.android.projects.ProjectListRoute
import de.cgi.android.timeentry.TimeEntryAddRoute
import de.cgi.android.timeentry.TimeEntryEditRoute
import de.cgi.android.timeentry.TimeEntryListRoute
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainAppScaffold(
    content: @Composable () -> Unit,
    router: Router
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,

        drawerContent = {
            DrawerHeader()
            DrawerBody(
                items = listOf(
                    MenuItem(
                        id = MenuId.Calender,
                        title = "Calender",
                        contentDescription = "Get help",
                        icon = FeatherIcons.Calendar,
                        selected = getMenuIdFromRoute(router.getCurrentRoute()) == MenuId.Calender
                    ),
                    MenuItem(
                        id = MenuId.Timetracking,
                        title = "Time tracking",
                        contentDescription = "Go to home screen",
                        icon = FeatherIcons.Clock,
                        selected = getMenuIdFromRoute(router.getCurrentRoute()) == MenuId.Timetracking
                    ),
                    MenuItem(
                        id = MenuId.Projects,
                        title = "Projects",
                        contentDescription = "Go to projects screen",
                        icon = FeatherIcons.Archive,
                        selected = getMenuIdFromRoute(router.getCurrentRoute()) == MenuId.Projects
                    ),
                    MenuItem(
                        id = MenuId.Dashboard,
                        title = "Dashboard",
                        contentDescription = "Go to dashboard screen",
                        icon = FeatherIcons.BarChart2,
                        selected = getMenuIdFromRoute(router.getCurrentRoute()) == MenuId.Dashboard
                    ),
                    MenuItem(
                        id = MenuId.Account,
                        title = "Account",
                        contentDescription = "Get help",
                        icon = FeatherIcons.User,
                        selected = getMenuIdFromRoute(router.getCurrentRoute()) == MenuId.Account
                    ),
                    MenuItem(
                        id = MenuId.Settings,
                        title = "Settings",
                        contentDescription = "Get help",
                        icon = FeatherIcons.Settings,
                        selected = getMenuIdFromRoute(router.getCurrentRoute()) == MenuId.Settings
                    ),

                ),
                onItemClick = {
                    when (it.id) {
                        MenuId.Timetracking -> router.showTimeEntryList()
                        MenuId.Projects -> router.showProjectList()
                        MenuId.Settings -> router.showSettings()
                        else -> router.showTimeEntryList()
                    }
                }
            )
        },
        content = {content()}
    )
}

fun getMenuIdFromRoute(route: String?): MenuId? {
    return when (route) {
        TimeEntryListRoute.route -> MenuId.Timetracking
        TimeEntryEditRoute.route -> MenuId.Timetracking
        TimeEntryAddRoute.route -> MenuId.Timetracking
        ProjectListRoute.route -> MenuId.Projects
        // Add other routes and their corresponding MenuId here
        else -> null
    }
}
