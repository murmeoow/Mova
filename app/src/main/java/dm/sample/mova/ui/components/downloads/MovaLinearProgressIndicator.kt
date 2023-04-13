package dm.sample.mova.ui.components.downloads

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun MovaLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
) {
    Canvas(
        modifier
            .progressSemantics(progress)
            .height(8.dp)
            .clip(RoundedCornerShape(100.dp))
    ) {
        val strokeWidth = size.height
        drawLinearIndicatorBackground(backgroundColor, strokeWidth)
        drawLinearIndicator(0f, progress, color, strokeWidth)
    }
}

private fun DrawScope.drawLinearIndicatorBackground(
    color: Color,
    strokeWidth: Float
) = drawLinearIndicator(0f, 1f, color, strokeWidth)

private fun DrawScope.drawLinearIndicator(
    startFraction: Float,
    endFraction: Float,
    color: Color,
    strokeWidth: Float
) {
    val width = size.width
    val height = size.height
    // Start drawing from the vertical center of the stroke
    val yOffset = height / 2

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * width
    val barEnd = (if (isLtr) endFraction else 1f - startFraction) * width

    // Progress line
    drawLine(color, Offset(barStart, yOffset), Offset(barEnd, yOffset), strokeWidth)
}