package de.cgi.android.auth.signin


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import de.cgi.android.auth.InputType
import de.cgi.android.auth.SignInState
import de.cgi.android.ui.components.AuthScreenContent
import de.cgi.android.ui.components.showToast

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun SignInScreen(
    signInState: SignInState?,
    onSignInEmailChanged: (String) -> Unit,
    onSignInPasswordChanged: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignInSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onGetUserRole: () -> Unit
) {
    val context = LocalContext.current

    AuthScreenContent(
        buttonText = "SIGN IN",
        inputFields = listOf(
            Pair(InputType.EMail, onSignInEmailChanged),
            Pair(InputType.Password, onSignInPasswordChanged)
        ),
        errorMessages = null,
        onButtonClick = onSignInClick,
        onAltButtonClick = onSignUpClick,
        altButtonText = "Sign up",
        altButtonQuestion = "Don't have an account?",
    )

    LaunchedEffect(key1 = signInState) {
        when (signInState) {
            is SignInState.Loading -> {
            }
            is SignInState.Authorized -> {
                context.showToast("Successfully logged in")
                onSignInSuccess()
                onGetUserRole()
            }
            is SignInState.Success -> {
                context.showToast("Successfully logged in")
                onSignInSuccess()
                onGetUserRole()
            }
            is SignInState.Failure -> {
                context.showToast(signInState.message ?: "Unknown error occurred")
            }
            is SignInState.Error -> {
                context.showToast(signInState.error ?: "Unknown error occurred")
            }
            else -> {
                context.showToast("Unknown Error occurred")
            }
        }
    }
}









