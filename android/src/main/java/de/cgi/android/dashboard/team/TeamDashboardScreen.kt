package de.cgi.android.dashboard.team

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Edit
import de.cgi.android.ui.components.*
import de.cgi.android.ui.theme.LocalColor
import de.cgi.android.ui.theme.LocalSpacing
import de.cgi.android.ui.theme.LocalTypography
import de.cgi.android.util.GenericError
import de.cgi.common.ResultState
import de.cgi.common.repository.ProjectNameProvider
import org.koin.androidx.compose.get
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TeamDashboardScreen(
    showTeamEditScreen: () -> Unit,
    isManager: Boolean,
    onNavigateToTeamDashboard: () -> Unit,
    onNavigateToPersonalDashboard: () -> Unit,
    onReloadData: () -> Unit,
    teamDashboardData: List<TeamDashboardData>,
    teamDashboardDataState: ResultState<List<TeamDashboardData>>,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { showTeamEditScreen() }) {
                Icon(imageVector = FeatherIcons.Edit, contentDescription = "Edit Team")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalSpacing.current.medium),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isManager) {
                TabMenu(
                    onNavigateToPersonalDashboard = { onNavigateToPersonalDashboard() },
                    onNavigateToTeamDashboard = { onNavigateToTeamDashboard() }
                )
                Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
            }

            SwitchWeeks(
                onUpdateDateAndReloadMinus = { },
                onUpdateDateAndReloadPlus = { },
                weekText = ""
            )

            TeamDashboard(teamDashboardDataState = teamDashboardDataState, onReloadData = onReloadData)

        }
    }
}
@Composable
fun TeamDashboard(
    teamDashboardDataState: ResultState<List<TeamDashboardData>>,
    onReloadData: () -> Unit
) {
    val projectNameProvider = get<ProjectNameProvider>()
    when (teamDashboardDataState) {
        is ResultState.Loading -> {
            CircularProgressIndicator()
        }
        is ResultState.Error -> {
            GenericError(onDismissAction = onReloadData)
        }
        is ResultState.Success -> {
            val teamDashboardData = teamDashboardDataState.data
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LocalSpacing.current.medium),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(teamDashboardData) { teamDashboardDataItem ->
                    println(teamDashboardDataItem.name)
                    val userId = teamDashboardDataItem.name
                    val dashboardData = teamDashboardDataItem.dataList

                    Text(
                        text = "User: $userId",
                        style = LocalTypography.current.headlineSmall,
                        color = LocalColor.current.black
                    )

                    Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

                    if (dashboardData.isNotEmpty()) {
                        val column2Weight = .5f // 70%
                        val column3Weight = .25f // 70%
                        val column4Weight = .25f // 70%

                        Column( // Changed from LazyColumn to Column
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Here is the header
                            Row(
                                Modifier.background(LocalColor.current.darkGrey),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TableCell(text = "Project", weight = column2Weight)
                                TableCell(text = "Total", weight = column3Weight)
                                TableCell(text = "%", weight = column4Weight)
                            }

                            dashboardData.forEach { projectSummary -> // Changed from items to forEach
                                val color = if (projectSummary.projectId != null) {
                                    LocalColor.current.lightGreen
                                } else {
                                    LocalColor.current.lightGrey
                                }

                                val backgroundColor = if (projectSummary.projectId != null) {
                                    color
                                } else {
                                    LocalColor.current.lightGrey
                                }

                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .background(color = backgroundColor)
                                ) {
                                    TableCell(
                                        text = projectNameProvider.getProjectNameById(
                                            projectSummary.projectId
                                        )
                                            ?: "Internal", weight = column2Weight
                                    )
                                    TableCell(
                                        text = projectSummary.duration.toString(),
                                        weight = column3Weight
                                    )
                                    TableCell(
                                        text = projectSummary.percentage.roundToInt().toString(),
                                        weight = column4Weight
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "No data available for this team.",
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}
