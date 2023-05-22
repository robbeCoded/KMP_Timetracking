package de.cgi.components.util

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import de.cgi.common.util.ResultState
import org.jetbrains.compose.web.dom.Text

@Composable
fun <T> AsyncData(
    resultState: ResultState<T?>?,
    loadingContent: @Composable () -> Unit = { Box(modifier = Modifier.fillMaxSize()) {Text("Loading")} },
    errorContent: @Composable () -> Unit = { Box(modifier = Modifier.fillMaxSize()) {Text("Error")} },
    content: @Composable (data: T?) -> Unit
) {
    resultState.let { state ->
        when (state) {
            is ResultState.Loading -> {
                loadingContent()
            }
            is ResultState.Error -> {
                errorContent()
            }
            null -> {
                content(null)
            }
            is ResultState.Success -> {
                content(state.data)
            }
        }
    }
}