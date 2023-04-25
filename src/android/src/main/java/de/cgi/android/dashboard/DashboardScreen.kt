package de.cgi.android.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.cgi.android.ui.components.PieChartView
import de.cgi.android.ui.components.SwitchWeeks
import de.cgi.android.ui.components.TabMenu
import de.cgi.android.ui.components.Table
import de.cgi.android.ui.theme.LocalSpacing
import de.cgi.android.util.AsyncData
import de.cgi.android.util.GenericError
import de.cgi.android.util.getCurrentDateTime
import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.ProjectMapProvider
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.get

@Composable
fun DashboardScreen(
    isManager: Boolean,
    onNavigateToTeamDashboard: () -> Unit,
    onNavigateToPersonalDashboard: () -> Unit,
    dashboardDataState: ResultState<List<TimeEntry>>,
    dashboardData: List<DashboardData>,
    onReloadDashboardData: () -> Unit,
    onUpdateDateAndReloadPlus: () -> Unit,
    onUpdateDateAndReloadMinus: () -> Unit,
    onGetSelectedDate: () -> LocalDate
) {

    val projectMapProvider = get<ProjectMapProvider>()
    val currentDate = getCurrentDateTime().date
    val weekText = if (onGetSelectedDate() == currentDate) {
        "This Week (${currentDate.dayOfYear / 7 + 1})"
    } else {
        "Calender Week ${onGetSelectedDate().dayOfYear / 7 + 1}"
    }

    LaunchedEffect(Unit) {
        onReloadDashboardData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalSpacing.current.medium),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SwitchWeeks(
            onUpdateDateAndReloadMinus = onUpdateDateAndReloadMinus,
            onUpdateDateAndReloadPlus = onUpdateDateAndReloadPlus,
            weekText = weekText
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        if (isManager) {
            TabMenu(
                onNavigateToPersonalDashboard = onNavigateToPersonalDashboard,
                onNavigateToTeamDashboard = onNavigateToTeamDashboard
            )
        }

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        AsyncData(resultState = dashboardDataState, errorContent = {
            GenericError(
                onDismissAction = onReloadDashboardData
            )
        }) {
            if (!it.isNullOrEmpty()) {
                if (dashboardData.first().duration.toSecondOfDay() == 0) {
                    Text(
                        text = "There are no time entries for the selected week.",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                } else {

                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                    ) {
                        PieChartView(dashboardData, projectMapProvider)
                    }

                    Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

                    Table(dashboardData, onReloadDashboardData, projectMapProvider)
                }
            } else {
                Text(
                    text = "There are no time entries for the selected week.",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

