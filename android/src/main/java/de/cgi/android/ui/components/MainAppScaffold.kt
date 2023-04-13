package de.cgi.android.ui.components

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import de.cgi.android.navigation.Router
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
                        icon = FeatherIcons.Calendar
                    ),
                    MenuItem(
                        id = MenuId.Timetracking,
                        title = "Time tracking",
                        contentDescription = "Go to home screen",
                        icon = FeatherIcons.Clock
                    ),
                    MenuItem(
                        id = MenuId.Projects,
                        title = "Projects",
                        contentDescription = "Go to settings screen",
                        icon = FeatherIcons.Archive
                    ),
                    MenuItem(
                        id = MenuId.Account,
                        title = "Account",
                        contentDescription = "Get help",
                        icon = FeatherIcons.User
                    ),
                    MenuItem(
                        id = MenuId.Settings,
                        title = "Settings",
                        contentDescription = "Get help",
                        icon = FeatherIcons.Settings
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
