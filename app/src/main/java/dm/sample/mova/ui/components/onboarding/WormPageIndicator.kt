package dm.sample.mova.ui.components.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @url https://www.jetpackcompose.app/snippets/WormPageIndicator
 */
@Composable
fun WormPageIndicator(
    totalPages: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    indicatorSize: Dp = 6.dp,
    activeColor: Color,
    color: Color,
    spacing: Dp = indicatorSize,
    selectedMultiplier: Int = 3
) {
    assert(
        value = currentPage in 0 until totalPages,
        lazyMessage = { "Current page index is out of range." }
    )

    val rowWidth = (indicatorSize * (selectedMultiplier + (totalPages - 1))) + (spacing * (totalPages - 1))

    Row(
        modifier = modifier
            .requiredWidth(rowWidth),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0 until totalPages) {
            val selected = i == currentPage

            val height = indicatorSize
            val width: Dp by animateDpAsState(
                if (selected) indicatorSize * selectedMultiplier else indicatorSize
            )

            Canvas(
                modifier = Modifier
                    .size(width, height),
                onDraw = {
                    drawRoundRect(
                        color = if (selected) activeColor else color,
                        cornerRadius = CornerRadius(height.toPx() / 2),
                        size = Size(width.toPx(), height.toPx())
                    )
                }
            )
        }
    }
}