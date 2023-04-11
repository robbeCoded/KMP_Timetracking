package de.cgi.android.auth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.cgi.android.navigation.Router
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalAnimationApi
fun NavGraphBuilder.authGraph(
    router: Router
) {
    navigation(
        route = AuthFeature.route,
        startDestination = AuthScreenRoute.route,
    ) {
        composable(AuthScreenRoute.route) {
            val viewModel = getViewModel<AuthViewModel>()
            val signUpState by viewModel.signUpState.collectAsState()
            val signInState by viewModel.signInState.collectAsState()

            AuthScreen(
                signInState = signInState,
                signUpState = signUpState,
                onSignInClick = viewModel::signIn,
                onSignUpClick = viewModel::signUp,
                isEmailValid = viewModel::isEmailValid,
                isPasswordValid = viewModel::isPasswordValid,
                onSignInEmailChanged = viewModel::signInEmailChanged,
                onSignInPasswordChanged = viewModel::signInPasswordChanged,
                onSignUpEmailChanged = viewModel::signUpEmailChanged,
                onSignUpPasswordChanged = viewModel::signUpPasswordChanged,
                onSignUpNameChanged = viewModel::signUpNameChanged,
                onSignInSuccess = router::showTimeEntryList,
            )
        }
    }
}