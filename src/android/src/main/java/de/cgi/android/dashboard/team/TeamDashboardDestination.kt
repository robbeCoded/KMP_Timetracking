package de.cgi.android.dashboard.team

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
fun TeamDashboardDestination(
    router: Router,
) {
    val userRepository = get<UserRepository>()
    val role = userRepository.getUserRole()
    val isManager = role == "team_manager"

    val viewModel = getViewModel<TeamDashboardViewModel>()
    val teamDashboardDataState by viewModel.dataListState.collectAsState()


    TeamDashboardScreen(
        showTeamEditScreen = { router.showTeamList() },
        isManager = isManager,
        onNavigateToTeamDashboard = { router.showTeamDashboard() },
        onNavigateToPersonalDashboard = { router.back() },
        onReloadData = viewModel::getUserIds,
        teamDashboardData = teamDashboardDataState.teamDashboardData,
        teamDashboardDataState = teamDashboardDataState.teamDashboardDataState,
    )
}