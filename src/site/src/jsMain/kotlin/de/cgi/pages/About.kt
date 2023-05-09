package de.cgi.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import de.cgi.common.auth.SignUpViewModel
import de.cgi.components.styles.MainButtonStyle
import de.cgi.components.styles.Theme
import de.cgi.components.widgets.TodoContainerStyle
import de.cgi.components.widgets.TodoInputStyle
import de.cgi.components.widgets.TodoStyle
import de.cgi.components.widgets.TodoTextStyle
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Page
@Composable
fun AboutPage() {
    val di = localDI()
    val viewModel: SignUpViewModel by di.instance()
    Column(
        modifier = Modifier.fillMaxSize().minWidth(600.px),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Column(Modifier.fillMaxSize()) {
            Column(
                Modifier.fillMaxWidth().maxWidth(800.px).align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SignUpForm(
                    onSignUpClick = viewModel::signUp,
                    onSignUpNameChanged = viewModel::signUpNameChanged,
                    onSignUpPasswordChanged = viewModel::signUpPasswordChanged,
                    onSignUpEmailChanged = viewModel::signUpEmailChanged
                )
            }

        }
    }
    Link("/", "Go Home")
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

    Form(attrs = listOf(TodoStyle, TodoContainerStyle).toAttrs {
        onSubmit { evt ->
            evt.preventDefault()
            onSignUpClick()
        }
    }) {
        Label(
            attrs = Modifier
                .classNames("form-label")
                .toAttrs(),
        ) {
            Text("Name")
        }
        Input(
            InputType.Text,
            attrs = listOf(TodoStyle, TodoTextStyle, TodoInputStyle)
                .toAttrs {
                    placeholder("Full Name")
                    name("name")
                    onChange {
                        name = it.value
                        onSignUpNameChanged(it.value)
                    }
                }
        )

        Label(
            attrs = Modifier
                .classNames("form-label")
                .toAttrs(),
        ) {
            Text("E-Mail")
        }
        Input(
            InputType.Text,
            attrs = listOf(TodoStyle, TodoTextStyle, TodoInputStyle)
                .toAttrs {
                    placeholder("E-Mail Address")
                    name("email")
                    onChange {
                        email = it.value
                        onSignUpEmailChanged(it.value)
                    }
                }
        )

        Label(
            attrs = Modifier
                .classNames("form-label")
                .toAttrs(),
        ) {
            Text("Password")
        }
        Input(
            InputType.Text,
            attrs = listOf(TodoStyle, TodoTextStyle, TodoInputStyle)
                .toAttrs {
                    placeholder("Password")
                    name("password")
                    onChange {
                        password = it.value
                        onSignUpPasswordChanged(it.value)
                    }
                }
        )
        Button(attrs = MainButtonStyle.toModifier()
            .height(40.px)
            .border(width = 0.px)
            .borderRadius(r = 5.px)
            .backgroundColor(Theme.Primary.rgb)
            .color(Colors.White)
            .cursor(Cursor.Pointer)
            .toAttrs()) {
            Text("Sign Up")
        }
    }
}