package de.cgi.android.ui.components

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
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronLeft
import compose.icons.feathericons.ChevronRight
import de.cgi.android.dashboard.DashboardData
import de.cgi.android.ui.theme.LocalColor
import de.cgi.android.ui.theme.LocalSpacing
import de.cgi.android.ui.theme.LocalTypography
import de.cgi.android.util.stringToColor
import de.cgi.common.repository.ProjectMapProvider
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation
import kotlin.math.roundToInt


@Composable
fun PieChartView(dashboardData: List<DashboardData>, projectMapProvider: ProjectMapProvider) {
    PieChart(
        pieChartData = PieChartData(
            slices = dashboardData.map {
                val color = if (it.projectId != null) {
                    val colorString = projectMapProvider.getProjectColorById(it.projectId)
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
    projectMapProvider: ProjectMapProvider
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
            val colorString = projectMapProvider.getProjectColorById(projectSummary.projectId)
            val color = if (colorString != null) {
                stringToColor(colorString)
            } else {
                LocalColor.current.itemColor
            }

            stringToColor(projectMapProvider.getProjectColorById(projectSummary.projectId) ?: "")
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
                    text = projectMapProvider.getProjectNameById(projectSummary.projectId)
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

@Composable
fun TabMenu(
    onNavigateToPersonalDashboard: () -> Unit,
    onNavigateToTeamDashboard: () -> Unit,
) {
    val tabs = listOf("Personal", "Team")
    var selectedTabIndex by remember { mutableStateOf(0) }
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
                    if (selectedTabIndex == 1) {
                        onNavigateToTeamDashboard()
                    }
                    if (selectedTabIndex == 0) {
                        onNavigateToPersonalDashboard()
                    }
                    selectedTabIndex = index
                }
            )
        }
    }

}

@Composable
fun SwitchWeeks(
    onUpdateDateAndReloadMinus: () -> Unit,
    onUpdateDateAndReloadPlus: () -> Unit,
    weekText: String,
) {
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

}