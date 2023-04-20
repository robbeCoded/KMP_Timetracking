package de.cgi.android.dashboard.team

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import compose.icons.FeatherIcons
import compose.icons.feathericons.Edit
import compose.icons.feathericons.Plus
import de.cgi.android.ui.theme.LocalColor
import de.cgi.android.ui.theme.LocalSpacing
import de.cgi.android.ui.theme.LocalTypography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TeamDashboardScreen(
    showTeamEditScreen: () -> Unit,
    isManager: Boolean,
    onNavigateToTeamDashboard: () -> Unit,
    onNavigateToPersonalDashboard: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { showTeamEditScreen() }) {
                Icon(imageVector = FeatherIcons.Edit, contentDescription = "Edit Team")
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalSpacing.current.medium),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isManager) {
                    val tabs = listOf("Personal", "Team")
                    var selectedTabIndex by remember { mutableStateOf(0) }
                    println("inside Tab menu")
                    TabRow(selectedTabIndex) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                text = {
                                    Text(
                                        title,
                                        color = LocalColor.current.actionSecondary,
                                        style = if (selectedTabIndex == index)
                                            LocalTypography.current.tabMenuSelected
                                        else LocalTypography.current.tabMenuNotSelected,
                                    )
                                },
                                selected = selectedTabIndex == index,
                                onClick = {
                                    selectedTabIndex = index
                                    if (selectedTabIndex == 1) {
                                        onNavigateToTeamDashboard()
                                    }
                                    if (selectedTabIndex == 0) {
                                        onNavigateToPersonalDashboard()
                                    }
                                }
                            )
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        }
    }
}