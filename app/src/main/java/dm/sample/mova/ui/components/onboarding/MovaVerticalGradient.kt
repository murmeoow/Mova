package dm.sample.mova.ui.components.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun MovaVerticalGradient(colors: List<Color>) {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors),
            ),
    )
}
