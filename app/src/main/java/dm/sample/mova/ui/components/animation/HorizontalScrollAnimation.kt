package dm.sample.mova.ui.components.animation

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HorizontalScrollAnimation(
    modifier: Modifier,
    content: @Composable BoxScope.() -> Unit,
    contentAlignment: Alignment = Alignment.TopStart,
) {
    val scrollState = rememberScrollState()
    var shouldAnimated by remember {
        mutableStateOf(true)
    }


    LaunchedEffect(key1 = shouldAnimated){
        if (shouldAnimated) {
            scrollState.animateScrollTo(
                value = scrollState.maxValue,
                animationSpec = tween(
                    durationMillis = 10000,
                    delayMillis = 200,
                    easing = CubicBezierEasing(0f,0f,0f,0f)
                )
            )
        } else {
            scrollState.animateScrollTo(
                value = 0,
                animationSpec = tween(
                    durationMillis = 10000,
                    delayMillis = 200,
                    easing = CubicBezierEasing(0f,0f,0f,0f)
                )
            )
        }
        shouldAnimated = !shouldAnimated
    }
    Box(
        modifier = modifier.horizontalScroll(scrollState, false),
        contentAlignment = contentAlignment,
    ) {
        content()
    }

}