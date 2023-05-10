package de.cgi.android.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.cgi.android.dashboard.team.DashboardDataPerUser
import de.cgi.android.ui.components.SwitchWeeks
import de.cgi.android.ui.components.TabMenu
import de.cgi.android.ui.components.Table
import de.cgi.android.ui.theme.LocalSpacing
import de.cgi.android.util.AsyncData
import de.cgi.android.util.GenericError
import de.cgi.common.util.getCurrentDateTime
import de.cgi.common.util.ResultState
import de.cgi.common.dashboard.DashboardDataPerProject
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.ProjectMapProvider
import kotlinx.datetime.LocalDate
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
fun DashboardScreen(
    onLoadTeamData: (List<String>) -> Unit,
    isManager: Boolean,
    onNavigateToTeamDashboard: () -> Unit,
    onNavigateToPersonalDashboard: () -> Unit,
    dashboardDataState: ResultState<List<TimeEntry>>,
    dashboardData: List<DashboardDataPerProject>,
    teamDashboardDataState: ResultState<List<List<TimeEntry>?>>?,
    teamDashboardData: List<DashboardDataPerUser>?,
    onReloadDashboardData: () -> Unit,
    onReloadTeamDashboardData: () -> Unit,
    onUpdateDateAndReloadPlus: () -> Unit,
    onUpdateDateAndReloadMinus: () -> Unit,
    onGetSelectedDate: () -> LocalDate
) {
    val di = localDI()
    val projectMapProvider: ProjectMapProvider by di.instance()
    val currentDate = getCurrentDateTime().date
    val weekText = if (onGetSelectedDate() == currentDate) {
        "This Week (${currentDate.dayOfYear / 7 + 1})"
    } else {
        "Calender Week ${onGetSelectedDate().dayOfYear / 7 + 1}"
    }

    val (selectedTab, setSelectedTab) = remember { mutableStateOf(0) }
    val isTeamDashboard = selectedTab != 0

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalSpacing.current.medium),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            SwitchWeeks(
                onUpdateDateAndReloadMinus = onUpdateDateAndReloadMinus,
                onUpdateDateAndReloadPlus = onUpdateDateAndReloadPlus,
                weekText = weekText
            )
        }

        item {
            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        }


        if (isManager) {
            item {
                TabMenu(
                    onNavigateToPersonalDashboard = { setSelectedTab(0) },
                    onNavigateToTeamDashboard = {
                        setSelectedTab(1)
                        onReloadTeamDashboardData()

                    },
                    onSelectTab = setSelectedTab,
                )
                Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
            }


        }
        if (isTeamDashboard) {
            item {
                TeamDashboardContent(
                    teamDashboardDataState = teamDashboardDataState,
                    onReloadTeamDashboardData = { onReloadDashboardData() },
                    teamDashboardData = teamDashboardData,
                    projectMapProvider = projectMapProvider
                )
            }
        }
    }


}

@Composable
fun TeamDashboardOneMember(
    dashboardDataPerUser: DashboardDataPerUser,
    projectMapProvider: ProjectMapProvider
) {
    Text(text = dashboardDataPerUser.userId)
    Table(dashboardDataPerUser.data, projectMapProvider)
}

@Composable
fun TeamDashboardContent(
    teamDashboardDataState: ResultState<List<List<TimeEntry>?>>?,
    onReloadTeamDashboardData: () -> Unit,
    teamDashboardData: List<DashboardDataPerUser>?,
    projectMapProvider: ProjectMapProvider
) {
    AsyncData(resultState = teamDashboardDataState, errorContent = {
        GenericError(
            onDismissAction = onReloadTeamDashboardData
        )
    }) {
        if (teamDashboardData.isNullOrEmpty()) {
            Text(
                text = "There are no time entries for the selected week.",
                textAlign = TextAlign.Center
            )
        } else {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
            ) {
                //PieChartView(teamDashboardData, projectMapProvider)
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column {
                    teamDashboardData.forEach { userDashboardData ->
                        TeamDashboardOneMember(
                            dashboardDataPerUser = userDashboardData,
                            projectMapProvider = projectMapProvider
                        )
                    }
                }
            }


        }

    }
}