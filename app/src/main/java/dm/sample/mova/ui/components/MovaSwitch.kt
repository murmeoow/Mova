package dm.sample.mova.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.theme.Dark3
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Grayscale200
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun MovaSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
) {
    MovaSwitchContent(
        checkedTrackColor = Primary500,
        uncheckedTrackColor = if (isDarkTheme()) Dark3 else Grayscale200,
        checked = checked,
        checkedThumbColor = White,
        uncheckedThumbColor = White,
        onCheckedChange = {
            onCheckedChange?.invoke(it)
        }
    )
}


/**
 * https://semicolonspace.com/android-jetpack-compose-switch-2/
 */
@Composable
private fun MovaSwitchContent(
    width: Dp = 44.dp,
    height: Dp = 24.dp,
    checkedTrackColor: Color,
    uncheckedTrackColor: Color,
    checkedThumbColor: Color,
    uncheckedThumbColor: Color,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?
) {
    val gapBetweenThumbAndTrackEdge: Dp = 2.dp
    var isChecked = checked

    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

    // To move thumb, we need to calculate the position (along x axis)
    val animatePosition = animateFloatAsState(
        targetValue = if (isChecked)
            with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }
        else
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }
    )

    Canvas(
        modifier = Modifier
            .size(width = width, height = height)
            .defaultMinSize(minWidth = width, minHeight = height)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        isChecked = isChecked.not()
                        onCheckedChange?.invoke(isChecked)
                    }
                )
            }
    ) {
        // Track
        drawRoundRect(
            color = if (isChecked) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(x = height.toPx(), y = height.toPx()),
            style = Fill
        )

        // Thumb
        drawCircle(
            color = if (isChecked) checkedThumbColor else uncheckedThumbColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            ),
        )
    }


}

@ShowkaseComposable(
    name = "Mova Switch",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMovaSwitch() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center,
        ) {
            var isChecked by remember {
                mutableStateOf(false)
            }
            MovaSwitch(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = isChecked.not()
                },
            )
        }
    }
}
