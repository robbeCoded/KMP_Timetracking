package de.cgi.android.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.cgi.R
import de.cgi.android.auth.InputType
import de.cgi.android.ui.theme.LocalColor
import de.cgi.android.ui.theme.LocalSpacing

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun AuthScreenContent(
    buttonText: String,
    inputFields: List<Pair<InputType, (String) -> Unit>>,
    errorMessages: List<Pair<Boolean, String>>?,
    onButtonClick: () -> Unit,
    onAltButtonClick: () -> Unit,
    altButtonText: String,
    altButtonQuestion: String,
) {
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val screenWidthDp = configuration.screenWidthDp.dp
    val cornerstone = painterResource(R.drawable.login_corner)
    val logo = painterResource(R.drawable.cgi_logo_rgb_white)
    val focusManager = LocalFocusManager.current
    val focusRequesters = List(inputFields.size) { FocusRequester() }

    Column(
        Modifier
            .padding(horizontal = LocalSpacing.current.medium, vertical = LocalSpacing.current.extraSmall)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(LocalColor.current.white),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeightDp / 5)
        ) {
            Image(
                painter = cornerstone, contentDescription = "", modifier = Modifier
                    .align(alignment = Alignment.TopEnd)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeightDp / 6)
        ) {
            Icon(
                painter = logo,
                contentDescription = "Logo",
                tint = LocalColor.current.cgiRed,
                modifier = Modifier
                    .size(screenWidthDp / 2)
                    .align(Alignment.BottomStart)
            )
        }

        inputFields.forEachIndexed { index, inputField ->
            TextInput(
                inputField.first,
                keyboardActions = KeyboardActions(
                    onNext = {
                        if (index + 1 < inputFields.size) {
                            focusRequesters[index + 1].requestFocus()
                        } else {
                            focusManager.clearFocus()
                            onButtonClick()
                        }
                    }),
                focusRequester = focusRequesters[index],
                onValueChanged = inputField.second
            )
            if (!errorMessages.isNullOrEmpty() && index < 2) {
                AnimatedVisibility(visible = errorMessages[index].first) {
                    Text(
                        text = errorMessages[index].second,
                        style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
                    )
                }
            }

        }

        Button(
            onClick = onButtonClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = LocalColor.current.cgiPurpleLight)
        ) {
            Text(buttonText, Modifier.padding(vertical = 8.dp))
        }
        Divider(
            color = Color.White.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 48.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                altButtonQuestion,
                color = LocalColor.current.black,
                fontSize = 20.sp
            )
            TextButton(onClick = onAltButtonClick) {
                Text(
                    text = altButtonText,
                    style = TextStyle(
                        color = LocalColor.current.actionPrimary
                    ),
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

