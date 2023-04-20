package de.cgi.android.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronLeft
import compose.icons.feathericons.ChevronRight
import de.cgi.android.ui.theme.LocalColor
import de.cgi.android.ui.theme.LocalSpacing
import de.cgi.android.ui.theme.LocalTypography
import de.cgi.android.util.*
import de.cgi.common.ResultState
import de.cgi.common.data.model.TimeEntry
import de.cgi.common.repository.ProjectNameProvider
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation
import org.koin.androidx.compose.get
import kotlin.math.roundToInt

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

    val projectNameProvider = get<ProjectNameProvider>()
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onUpdateDateAndReloadMinus() }) {
                Icon(
                    imageVector = FeatherIcons.ChevronLeft,
                    contentDescription = "Navigate to previous week"
                )
            }
            Spacer(modifier = Modifier.width(LocalSpacing.current.medium))

            Text(text = weekText, style = LocalTypography.current.headlineSmall)

            Spacer(modifier = Modifier.width(LocalSpacing.current.medium))
            IconButton(onClick = { onUpdateDateAndReloadPlus() }) {
                Icon(
                    imageVector = FeatherIcons.ChevronRight,
                    contentDescription = "Navigate to next week"
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AsyncData(resultState = dashboardDataState, errorContent = {
            GenericError(
                onDismissAction = onReloadDashboardData
            )
        }) {
            if (!it.isNullOrEmpty()) {
                if (dashboardData.first().duration.toSecondOfDay() == 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 10.dp),
                    ) {
                        Text(
                            text = "There are no time entries for the selected week.",
                            modifier = Modifier.align(
                                Alignment.Center
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                    ) {
                        PieChartView(dashboardData, projectNameProvider)
                    }
                    Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

                    Table(dashboardData, onReloadDashboardData, projectNameProvider)
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 10.dp),
                ) {
                    Text(
                        text = "There are no time entries for the selected week.",
                        modifier = Modifier.align(
                            Alignment.Center
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun PieChartView(dashboardData: List<DashboardData>, projectNameProvider: ProjectNameProvider) {
    PieChart(
        pieChartData = PieChartData(
            slices = dashboardData.map {
                val color = if (it.projectId != null) {
                    val colorString = projectNameProvider.getProjectColorById(it.projectId)
                    if (colorString != null) {
                        stringToColor(colorString)
                    } else {
                        LocalColor.current.itemColor
                    }
                } else {
                    LocalColor.current.itemColor
                }

                PieChartData.Slice(it.percentage.toFloat(), color)
            }
        ),
        // Optional properties.
        animation = simpleChartAnimation(),
        sliceDrawer = SimpleSliceDrawer(50f)
    )
}


@Composable
fun Table(
    dashboardDataList: List<DashboardData>,
    reloadDashboardData: () -> Unit,
    projectNameProvider: ProjectNameProvider
) {
    val column2Weight = .5f // 70%
    val column3Weight = .25f // 70%
    val column4Weight = .25f // 70%

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Here is the header
        item {
            Row(
                Modifier.background(LocalColor.current.darkGrey),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TableCell(text = "Project", weight = column2Weight)
                TableCell(text = "Total", weight = column3Weight)
                TableCell(text = "%", weight = column4Weight)
            }
        }
        items(dashboardDataList) { projectSummary ->
            val colorString = projectNameProvider.getProjectColorById(projectSummary.projectId)
            val color = if (colorString != null) {
                stringToColor(colorString)
            } else {
                LocalColor.current.itemColor
            }

            stringToColor(projectNameProvider.getProjectColorById(projectSummary.projectId) ?: "")
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
                    text = projectNameProvider.getProjectNameById(projectSummary.projectId)
                        ?: "Internal", weight = column2Weight
                )
                TableCell(text = projectSummary.duration.toString(), weight = column3Weight)
                TableCell(
                    text = projectSummary.percentage.roundToInt().toString(),
                    weight = column4Weight
                )
            }
        }

    }
}


@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .height(60.dp)
            .border(1.dp, Color.Black)
            .weight(weight, fill = true)
            .padding(8.dp)
    )
}
