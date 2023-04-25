package de.cgi.android.auth.signup

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import de.cgi.android.auth.InputType
import de.cgi.android.auth.SignUpState
import de.cgi.android.ui.components.AuthScreenContent
import de.cgi.android.ui.components.showToast


@Composable
fun SignUpScreen(
    signUpState: SignUpState?,
    isEmailValid: () -> Boolean,
    isPasswordValid: () -> Boolean,
    onSignUpPasswordChanged: (String) -> Unit,
    onSignUpEmailChanged: (String) -> Unit,
    onSignUpNameChanged: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignUpSuccess: () -> Unit,
) {

    val context = LocalContext.current
    val signInEmailError = remember { mutableStateOf(false) }
    val signInPasswordError = remember { mutableStateOf(false) }

    AuthScreenContent(
        inputFields = listOf(
            Pair(InputType.EMail, onSignUpEmailChanged),
            Pair(InputType.Name, onSignUpNameChanged),
            Pair(InputType.Password, onSignUpPasswordChanged)
        ),
        errorMessages = listOf(
            Pair(signInEmailError.value, "Not a valid e-mail address"),
            Pair(signInPasswordError.value, "Password must be at least 8 characters")
        ),
        buttonText = "SIGN UP",
        onButtonClick = {
            if (isEmailValid() && isPasswordValid()) {
                onSignUpClick()
            } else {
                signInEmailError.value = true
                signInPasswordError.value = true
            }
        },
        altButtonQuestion = "Already have an account?",
        altButtonText = "Sign in",
        onAltButtonClick = onSignUpSuccess
    )

    LaunchedEffect(key1 = signUpState) {
        when (signUpState) {
            is SignUpState.Loading -> {
                context.showToast("Loading")
            }
            is SignUpState.Success -> {
                context.showToast("Successfully Signed Up")
                onSignUpSuccess()
            }
            is SignUpState.Failure -> {
                context.showToast(signUpState.message ?: "Unknown error occurred")
            }
            is SignUpState.Error -> {
                context.showToast(signUpState.error ?: "Unknown error occurred")
            }
            else -> {
                context.showToast("Unknown error occurred")
            }
        }
    }
}
