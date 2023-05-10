package de.cgi.android.dashboard.team

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.cgi.android.ui.components.*
import de.cgi.android.ui.theme.LocalSpacing
import de.cgi.android.util.GenericError
import de.cgi.common.util.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.ProjectMapProvider
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TeamDashboardScreen(
    isManager: Boolean,
    onNavigateToTeamDashboard: () -> Unit,
    onNavigateToPersonalDashboard: () -> Unit,
    onReloadData: () -> Unit,
    teamDashboardData: List<DashboardDataPerUser>,
    teamDashboardDataState: ResultState<List<List<TimeEntry>?>>,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalSpacing.current.medium),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isManager) {

            }

            SwitchWeeks(
                onUpdateDateAndReloadMinus = { },
                onUpdateDateAndReloadPlus = { },
                weekText = ""
            )

            TeamDashboard(
                teamDashboardDataState = teamDashboardDataState,
                onReloadData = onReloadData
            )

        }
    }
}

@Composable
fun TeamDashboard(
    teamDashboardDataState: ResultState<List<List<TimeEntry>?>>,
    onReloadData: () -> Unit
) {
    val di = localDI()
    val projectMapProvider: ProjectMapProvider by di.instance()
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
            ) {/*
                items(teamDashboardData) { teamDashboardDataItem ->
                    println(teamDashboardDataItem.userId)
                    val userId = teamDashboardDataItem.userId
                    val dashboardData = teamDashboardDataItem.data

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
                                        text = projectMapProvider.getProjectNameById(
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
                } */
            }
        }
    }
}
