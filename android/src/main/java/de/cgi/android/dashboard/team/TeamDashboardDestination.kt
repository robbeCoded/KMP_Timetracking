package de.cgi.android.dashboard.team

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import de.cgi.android.navigation.Router
import de.cgi.common.UserRepository
import org.koin.androidx.compose.get

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TeamDashboardDestination(
    router: Router,
) {
    val userRepository = get<UserRepository>()
    val role = userRepository.getUserRole()
    val isManager = role == "team_manager"

    TeamDashboardScreen(
        showTeamEditScreen = { router.showTeamList() },
        isManager = isManager,
        onNavigateToTeamDashboard = { router.showTeamDashboard() },
        onNavigateToPersonalDashboard = { router.back() },
    )
}