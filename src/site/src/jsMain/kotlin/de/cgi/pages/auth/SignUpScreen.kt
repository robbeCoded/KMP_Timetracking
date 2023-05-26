package de.cgi.pages.auth

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.toAttrs
import de.cgi.common.auth.SignUpState
import de.cgi.common.auth.SignUpViewModel
import de.cgi.components.layouts.AuthLayout
import de.cgi.components.styles.AuthHeading
import de.cgi.components.styles.Label
import de.cgi.components.styles.VerticalSpacer
import de.cgi.components.util.Res
import de.cgi.components.widgets.AddEditFormButton
import de.cgi.components.widgets.InputFieldStyleAuth
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.PasswordInput
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextInput
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Page(routeOverride = "signup")
@Composable
fun SignUpScreen() {
    val di = localDI()
    val viewModel: SignUpViewModel by di.instance()
    val signUpState by viewModel.signUpState.collectAsState()
    val ctx = rememberPageContext()


    AuthLayout(title = "Sign Up") {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) {
            Box(modifier = Modifier.width(40.percent))
            Column(
                modifier = Modifier
                    .width(30.percent)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Div(AuthHeading.toAttrs()) {
                    Text("CGI")
                }
                VerticalSpacer(16)
                SignUpForm(
                    onSignUpClick = viewModel::signUp,
                    onSignUpEmailChanged = viewModel::signUpEmailChanged,
                    onSignUpPasswordChanged = viewModel::signUpPasswordChanged,
                    onSignUpNameChanged = viewModel::signUpNameChanged
                )

                VerticalSpacer(32)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Already have an account?")
                    Link(path = "/auth/signin", text = "Sign In")
                }
            }
            Box(
                modifier = Modifier
                    .padding(16.px)
                    .width(40.percent),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    modifier = Modifier
                        .height(300.px)
                        .width(300.px),
                    src = Res.Image.loginCorner,
                    desc = "Profile Picture"
                )
            }
        }

    }

    when (signUpState) {
        is SignUpState.Success -> {
            ctx.router.navigateTo("/auth/signin")
        }
        is SignUpState.Loading -> {}
        is SignUpState.Failure -> {}
        is SignUpState.Error -> {}
        else -> {}
    }
}


@Composable
fun SignUpForm(
    onSignUpClick: () -> Unit,
    onSignUpNameChanged: (String) -> Unit,
    onSignUpEmailChanged: (String) -> Unit,
    onSignUpPasswordChanged: (String) -> Unit,
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(modifier = Modifier.width(60.percent)) {

        Div(Label.toAttrs()) {
            Text("Name")
        }
        TextInput(
            value = name,
            attrs = listOf(InputFieldStyleAuth)
                .toAttrs {
                    onInput {
                        name = it.value
                        onSignUpNameChanged(it.value)
                    }
                }
        )
        VerticalSpacer(16)

        Div(Label.toAttrs()) {
            Text("E-Mail")
        }
        TextInput(
            value = email,
            attrs = listOf(InputFieldStyleAuth)
                .toAttrs {
                    onInput {
                        email = it.value
                        onSignUpEmailChanged(it.value)
                    }
                }
        )

        VerticalSpacer(16)

        Div(Label.toAttrs()) {
            Text("Password")
        }
        PasswordInput(
            value = password,
            attrs = listOf(InputFieldStyleAuth)
                .toAttrs {
                    onInput {
                        password = it.value
                        onSignUpPasswordChanged(it.value)
                    }
                }
        )

        VerticalSpacer(32)

        AddEditFormButton(
            onClick = {
                onSignUpClick()
            },
            text = "Sign Up"
        )
    }
}