package de.cgi.android.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.dashboard.team.TeamDashboardViewModel
import de.cgi.android.navigation.Router
import de.cgi.common.UserRepository
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DashboardScreenDestination(
    router: Router,
) {
    val di = localDI()
    val viewModel: DashboardViewModel by di.instance()

    val userRepository: UserRepository by di.instance()
    val role = userRepository.getUserRole()
    val isManager = role == "team_manager"

    val teamViewModel: TeamDashboardViewModel by di.instance()
    val teamDashboardDataState by teamViewModel.dataListState.collectAsState()


    val dashboardDataState by viewModel.listState.collectAsState()

    DashboardScreen(
        isManager = isManager,
        onNavigateToTeamDashboard = { router.showTeamDashboard() },
        onNavigateToPersonalDashboard = { router.showDashboard() },
        dashboardDataState = dashboardDataState.dashboardDataState,
        dashboardData = dashboardDataState.dashboardData,
        teamDashboardDataState = if (isManager) {
            teamDashboardDataState.teamDashboardDataState
        } else {
            null
        },
        teamDashboardData = if (isManager) {
            teamDashboardDataState.teamDashboardData
        } else {
            null
        },
        onReloadDashboardData = {},//viewModel::getTimeEntries,
        onLoadTeamData = teamViewModel::fetchTeamDashboardData,
        onReloadTeamDashboardData = teamViewModel::getUserIds,
        onUpdateDateAndReloadPlus = viewModel::updateSelectedDateAndReloadPlus,
        onUpdateDateAndReloadMinus = viewModel::updateSelectedDateAndReloadMinus,
        onGetSelectedDate = viewModel::getSelectedDate
    )


}