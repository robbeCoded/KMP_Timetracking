package de.cgi.android

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import de.cgi.android.navigation.Screen
import de.cgi.android.timetracking.TimeEntryListItem
import de.cgi.android.timetracking.TimeEntryViewModel
import de.cgi.android.ui.components.*
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TimeEntryScreen(
    viewModel: TimeEntryViewModel = getViewModel<TimeEntryViewModel>(),
    navController: NavController,
) {
    val title = "Timetracking"
    val timeEntries by viewModel.timeEntries.collectAsState(emptyList())
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { navController.navigate(Screen.AddEditTimeEntryScreen.route) }
            ) {
                Icon(imageVector = FeatherIcons.Plus, contentDescription = "Add time entry")
            }
        },
        floatingActionButtonPosition = FabPosition.End,

        scaffoldState = scaffoldState,
        topBar = {
            de.cgi.android.ui.components.AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }, title = title
            )
        },
        drawerContent = {
            DrawerHeader()
            DrawerBody(
                items = listOf(
                    MenuItem(
                        id = MenuId.Calender,
                        title = "Kalender",
                        contentDescription = "Navigiere zu Kalender",
                        icon = FeatherIcons.Clock
                    ),
                    MenuItem(
                        id = MenuId.Timetracking,
                        title = "Zeiterfassung",
                        contentDescription = "Navigiere zu Zeiterfassung",
                        icon = FeatherIcons.Calendar
                    ),
                    MenuItem(
                        id = MenuId.Projects,
                        title = "Projekte",
                        contentDescription = "Navigiere zu Projekte",
                        icon = FeatherIcons.Folder
                    ),
                    MenuItem(
                        id = MenuId.Account,
                        title = "Account",
                        contentDescription = "Navigiere zu Account",
                        icon = FeatherIcons.User
                    ),
                    MenuItem(
                        id = MenuId.Settings,
                        title = "Einstellungen",
                        contentDescription = "Navigiere zu Einstellungen",
                        icon = FeatherIcons.Settings
                    )

                ), onItemClick = {
                    when (it.id) {
                        MenuId.Calender -> {
                            navController.navigate(Screen.CalenderScreen.route)
                        }
                        MenuId.Timetracking -> {
                            navController.navigate(Screen.TimeEntryScreen.route)
                        }
                        MenuId.Projects -> {
                            navController.navigate(Screen.ProjectScreen.route)
                        }
                        MenuId.Account -> {
                            navController.navigate(Screen.AccountScreen.route)
                        }
                        MenuId.Settings -> {
                            navController.navigate(Screen.SettingsScreen.route)
                        }
                    }


                })
        }) {
        if (timeEntries.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 2.dp
                )
            }
        } else {
            LazyColumn {
                items(timeEntries) { timeEntry ->
                    TimeEntryListItem(
                        timeEntry,
                        onClick =  { navController.navigate(Screen.AddEditTimeEntryScreen.route + "?timeentry_id=${timeEntry.id}") }
                    )
                }
            }
        }





    }
}

