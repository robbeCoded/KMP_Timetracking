package de.cgi.android.auth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun AuthScreen(
    signInState: SignInState?,
    signUpState: SignUpState?,
    isEmailValid: () -> Boolean,
    isPasswordValid: () -> Boolean,
    onSignInEmailChanged: (String) -> Unit,
    onSignInPasswordChanged: (String) -> Unit,
    onSignUpPasswordChanged: (String) -> Unit,
    onSignUpEmailChanged: (String) -> Unit,
    onSignUpNameChanged: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onSignInSuccess: () -> Unit,
) {

    // Create states for TextField inputs
    val signUpEmail = remember { mutableStateOf("") }
    val signUpName = remember { mutableStateOf("") }
    val signUpPassword = remember { mutableStateOf("") }
    val signInEmail = remember { mutableStateOf("") }
    val signInPassword = remember { mutableStateOf("") }

    val signUpEmailError = remember { mutableStateOf(false) }
    val signUpPasswordError = remember { mutableStateOf(false) }


    Column {
        TextField(
            value = signUpEmail.value,
            onValueChange = { value ->
                signUpEmail.value = value
                onSignUpEmailChanged(value)
            },
            label = { Text("Email") },
            isError = signUpEmailError.value
        )
        AnimatedVisibility(visible = signUpEmailError.value) {
            Text(
                text = "Not a valid e-mail address",
                style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
            )
        }

        TextField(
            value = signUpName.value,
            onValueChange = { value ->
                signUpName.value = value
                onSignUpNameChanged(value)
            },
            label = { Text("Name") }
        )

        TextField(
            value = signUpPassword.value,
            onValueChange = { value ->
                signUpPassword.value = value
                onSignUpPasswordChanged(value)
            },
            label = { Text("Password") },
            isError = signUpPasswordError.value,
        )
        AnimatedVisibility(visible = signUpPasswordError.value) {
            Text(
                text = "Password must be at least 8 characters",
                style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
            )
        }

        Button(onClick = {
            if (isEmailValid() && isPasswordValid()) {
                onSignUpClick()
                signUpPasswordError.value = false
                signUpEmailError.value = false
            } else {
                signUpPasswordError.value = true
                signUpEmailError.value = true
            }
        }) {
            Text("Sign Up")
        }

        // ... SignIn UI components ...
        TextField(
            value = signInEmail.value,
            onValueChange = { value ->
                signInEmail.value = value
                onSignInEmailChanged(value)
            },
            label = { Text("Email") }
        )

        TextField(
            value = signInPassword.value,
            onValueChange = { value ->
                signInPassword.value = value
                onSignInPasswordChanged(value)
            },
            label = { Text("Password") }
        )

        Button(onClick = { onSignInClick() }) {
            Text("Sign In")
        }
    }

    LaunchedEffect(key1 = signUpState) {
        when (signUpState) {
            is SignUpState.Loading -> {
                println("SignUp Loading")
            }
            is SignUpState.Success -> {
                println("SignUpState Success")
            }

            is SignUpState.Failure -> {
                println("SignUpFailure")
            }
            is SignUpState.Error -> {
                println("SignUp Error")
            }
            else -> {
                println("SignUp other error")
            }
        }
    }

    LaunchedEffect(key1 = signInState) {
        when (signInState) {
            is SignInState.Loading -> {
                println("Loading")
            }
            is SignInState.Authorized -> { onSignInSuccess() }
            is SignInState.Success -> {
                onSignInSuccess()
            }
            is SignInState.Failure -> {
                println("Failure")
            }
            is SignInState.Error -> {
                println("Error")
            }
            else -> {
                println("Else Error")
            }
        }
    }
}