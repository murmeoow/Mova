package dm.sample.mova.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import dm.sample.mova.R

@Composable
fun MovaCircularProgressBar(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angel by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    Image(painter = painterResource(id = R.drawable.ic_mova_progress_bar),
        contentDescription = "progress bar",
        modifier = modifier
            .rotate(angel)
    )
}