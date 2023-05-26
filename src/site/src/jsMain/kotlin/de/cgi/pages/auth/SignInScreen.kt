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
import de.cgi.common.auth.SignInState
import de.cgi.common.auth.SignInViewModel
import de.cgi.components.layouts.AuthLayout
import de.cgi.components.styles.AuthHeading
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

@Page(routeOverride = "signin")
@Composable
fun SingInScreen() {
    val di = localDI()
    val viewModel: SignInViewModel by di.instance()
    val signInState by viewModel.signInState.collectAsState()
    val ctx = rememberPageContext()

    AuthLayout(title = "Sign In") {
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
                SignInForm(
                    onSignInClick = viewModel::signIn,
                    onSignInEmailChanged = viewModel::signInEmailChanged,
                    onSignInPasswordChanged = viewModel::signInPasswordChanged
                )

                VerticalSpacer(32)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Don't have an account?")
                    Link(path = "/auth/signup", text = "Sign Up")
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

    when (signInState) {
        is SignInState.Success -> {
            ctx.router.navigateTo("/timeentry/list")
        }
        is SignInState.Loading -> {}
        is SignInState.Failure -> {}
        is SignInState.Error -> {}
        else -> {}
    }
}


@Composable
fun SignInForm(
    onSignInClick: () -> Unit,
    onSignInEmailChanged: (String) -> Unit,
    onSignInPasswordChanged: (String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.width(60.percent)) {
        Div(de.cgi.components.styles.Label.toAttrs()) {
            Text("E-Mail")
        }
        TextInput(
            value = email,
            attrs = listOf(InputFieldStyleAuth)
                .toAttrs {
                    onInput {
                        email = it.value
                        onSignInEmailChanged(it.value)
                    }
                }
        )

        VerticalSpacer(16)

        Div(de.cgi.components.styles.Label.toAttrs()) {
            Text("Password")
        }
        PasswordInput(
            value = password,
            attrs = listOf(InputFieldStyleAuth)
                .toAttrs {
                    onInput {
                        password = it.value
                        onSignInPasswordChanged(it.value)
                    }
                }
        )

        VerticalSpacer(32)

        AddEditFormButton(
            onClick = {
                onSignInClick()
            },
            text = "Sign In"
        )
    }
}