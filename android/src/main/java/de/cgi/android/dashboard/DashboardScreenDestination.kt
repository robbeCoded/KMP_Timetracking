package de.cgi.android.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.navigation.Router
import de.cgi.common.UserRepository
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DashboardScreenDestination(
    router: Router,
) {
    val viewModel = getViewModel<DashboardViewModel>()

    val userRepository = get<UserRepository>()
    val role = userRepository.getUserRole()
    val isManager = role == "team_manager"
    println(role)

    val dashboardDataState by viewModel.listState.collectAsState()

    DashboardScreen(
        isManager = isManager,
        onNavigateToTeamDashboard = { router.showTeamDashboard() },
        onNavigateToPersonalDashboard = { router.back() },
        dashboardDataState = dashboardDataState.dashboardDataState,
        dashboardData = dashboardDataState.dashboardData,
        onReloadDashboardData = viewModel::getTimeEntries,
        onUpdateDateAndReloadPlus = viewModel::updateSelectedDateAndReloadPlus,
        onUpdateDateAndReloadMinus = viewModel::updateSelectedDateAndReloadMinus,
        onGetSelectedDate = viewModel::getSelectedDate
    )


}