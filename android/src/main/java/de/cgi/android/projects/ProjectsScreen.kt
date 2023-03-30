package de.cgi.android.projects

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import de.cgi.android.destinations.*
import de.cgi.android.timetracking.TimeEntryListItem
import de.cgi.android.ui.components.*
import de.cgi.android.timetracking.TimeEntryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun ProjectsScreen(
    viewModel: TimeEntryViewModel = getViewModel<TimeEntryViewModel>(),
    navigator: DestinationsNavigator,
) {


    val title = "Projects"
    val timeEntries by viewModel.timeEntries.collectAsState(emptyList())
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
                            navigator.navigate(CalenderScreenDestination())
                        }
                        MenuId.Timetracking -> {
                            navigator.navigate(TimeEntryScreenDestination())
                        }
                        MenuId.Projects -> {
                            navigator.navigate(ProjectsScreenDestination())
                        }
                        MenuId.Account -> {
                            navigator.navigate(AccountScreenDestination())
                        }
                        MenuId.Settings -> {
                            navigator.navigate(SettingsScreenDestination())
                        }
                    }


                })
        })
    {
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
                    TimeEntryListItem(timeEntry)
                }
            }
        }

    }
}

