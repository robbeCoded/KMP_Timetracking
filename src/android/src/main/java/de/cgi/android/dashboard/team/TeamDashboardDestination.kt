package de.cgi.android.dashboard.team

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.cgi.android.navigation.Router
import de.cgi.common.UserRepository
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TeamDashboardDestination(
    router: Router,
) {
    val di = localDI()
    val userRepository: UserRepository by di.instance()
    val role = userRepository.getUserRole()
    val isManager = role == "team_manager"

    val viewModel: TeamDashboardViewModel by di.instance()
    val teamDashboardDataState by viewModel.dataListState.collectAsState()


    TeamDashboardScreen(
        isManager = isManager,
        onNavigateToTeamDashboard = { router.showTeamDashboard() },
        onNavigateToPersonalDashboard = { router.back() },
        onReloadData = viewModel::getUserIds,
        teamDashboardData = teamDashboardDataState.teamDashboardData,
        teamDashboardDataState = teamDashboardDataState.teamDashboardDataState,
    )
}