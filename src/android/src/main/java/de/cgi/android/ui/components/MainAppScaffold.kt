package de.cgi.android.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import de.cgi.android.account.AccountScreenRoute
import de.cgi.android.calender.CalenderScreenRoute
import de.cgi.android.dashboard.DashboardScreenRoute
import de.cgi.android.navigation.Router
import de.cgi.android.projects.ProjectAddRoute
import de.cgi.android.projects.ProjectEditRoute
import de.cgi.android.projects.ProjectListRoute
import de.cgi.android.settings.SettingsHomeRoute
import de.cgi.android.timeentry.TimeEntryAddRoute
import de.cgi.android.timeentry.TimeEntryEditRoute
import de.cgi.android.timeentry.TimeEntryListRoute
import de.cgi.android.ui.theme.LocalColor
import de.cgi.common.UserRepository
import kotlinx.coroutines.launch
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainAppScaffold(
    content: @Composable () -> Unit,
    router: Router
) {
    val di = localDI()
    val userRepository: UserRepository by di.instance()

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val userName = userRepository.getUserName()

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
            DrawerHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LocalColor.current.darkGrey),
                userName = userName
            )
            DrawerBody(
                modifier = Modifier
                    .background(LocalColor.current.lightGrey)
                    .fillMaxSize(),
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
                        MenuId.Dashboard -> router.showDashboard()
                        MenuId.Account -> router.showAccount()
                        MenuId.Calender -> router.showCalender()
                    }
                }
            )
        },
        content = { content() }
    )
}

fun getMenuIdFromRoute(route: String?): MenuId? {
    return when (route) {
        TimeEntryListRoute.route -> MenuId.Timetracking
        TimeEntryEditRoute.route -> MenuId.Timetracking
        TimeEntryAddRoute.route -> MenuId.Timetracking
        ProjectListRoute.route -> MenuId.Projects
        DashboardScreenRoute.route -> MenuId.Dashboard
        ProjectAddRoute.route -> MenuId.Projects
        ProjectEditRoute.route -> MenuId.Projects
        AccountScreenRoute.route -> MenuId.Account
        CalenderScreenRoute.route -> MenuId.Calender
        SettingsHomeRoute.route -> MenuId.Settings
        else -> null
    }
}
