package dm.sample.mova.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import dm.sample.mova.ui.theme.Typography
import dm.sample.mova.ui.utils.isDarkTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dm.sample.mova.ui.theme.Dark1
import dm.sample.mova.ui.theme.Dark2
import dm.sample.mova.ui.theme.Dark3
import dm.sample.mova.ui.theme.Grayscale200
import dm.sample.mova.ui.theme.Grayscale50
import dm.sample.mova.ui.theme.Grayscale900
import dm.sample.mova.ui.theme.Primary400
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.Shapes
import dm.sample.mova.ui.theme.White

private val DarkColorPalette = darkColors(
    primary = Primary500,
    primaryVariant = Primary400,
    background = Dark1,
    onPrimary = White,
    onBackground = White,
    secondary = Dark3, // Borders, lines color
    surface = Dark2
)

private val LightColorPalette = lightColors(
    primary = Primary500,
    primaryVariant = Primary400,
    background = White,
    onPrimary = White,
    onBackground = Grayscale900,
    secondary = Grayscale200,
    surface = Grayscale50
)

@Composable
fun MovaTheme(
    darkTheme: Boolean = isDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        systemUiController.setNavigationBarColor(
            color = Dark1
        )
        systemUiController.statusBarDarkContentEnabled = false
        DarkColorPalette
    } else {
        systemUiController.setNavigationBarColor(
            color = White
        )
        systemUiController.statusBarDarkContentEnabled = true
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

