import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import de.cgi.android.ui.theme.*

private val LightColorPalette = lightColors(
    primary = Color(0xFFFFFCF3),
    background = Color(0xFFFFFDF6),
    secondary = Color(0xFF03DAC6)
)

private val DarkColorPalette = lightColors(
    primary = Color(0xFF1976D2),
    primaryVariant = Color(0xFF0D47A1),
    secondary = Color(0xFF03DAC6)
)

@Composable
fun TimetrackingTheme(content: @Composable () -> Unit) {

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalColor provides CustomColors(),
        LocalTypography provides TimetrackingTypography(),
    ) {
        MaterialTheme(
            colors = LightColorPalette,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }

}
