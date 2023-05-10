package de.cgi.android.auth

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.cgi.android.auth.signin.SignInScreen
import de.cgi.android.auth.signup.SignUpScreen
import de.cgi.android.navigation.Router
import de.cgi.common.auth.SignInViewModel
import de.cgi.common.auth.SignUpViewModel
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@ExperimentalAnimationApi
fun NavGraphBuilder.authGraph(
    router: Router
) {
    navigation(
        route = AuthFeature.route,
        startDestination = SignInScreenRoute.route,
    ) {
        composable(SignInScreenRoute.route) {
            val di = localDI()
            val viewModel: SignInViewModel by di.instance()
            val signInState by viewModel.signInState.collectAsState()

            SignInScreen(
                signInState = signInState,
                onSignInClick = viewModel::signIn,
                onSignInEmailChanged = viewModel::signInEmailChanged,
                onSignInPasswordChanged = viewModel::signInPasswordChanged,
                onSignInSuccess = router::showTimeEntryList,
                onSignUpClick = router::showSignUp,
                onGetUserRole = viewModel::getUserRole
            )
        }

        composable(SignUpScreenRoute.route) {
            val di = localDI()
            val viewModel: SignUpViewModel by di.instance()
            val signUpState by viewModel.signUpState.collectAsState()

            SignUpScreen(
                signUpState = signUpState,
                isEmailValid = viewModel::isEmailValid,
                isPasswordValid = viewModel::isPasswordValid,
                onSignUpPasswordChanged = viewModel::signUpPasswordChanged,
                onSignUpEmailChanged = viewModel::signUpEmailChanged,
                onSignUpNameChanged = viewModel::signUpNameChanged,
                onSignUpClick = viewModel::signUp,
                onSignUpSuccess = router::showSignIn,
            )
        }
    }
}