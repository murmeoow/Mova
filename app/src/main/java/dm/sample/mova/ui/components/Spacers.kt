package dm.sample.mova.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun MovaVerticalSpacer(
    height: Dp,
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    )
}

@Composable
fun MovaHorizontalSpacer(
    width: Dp,
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .width(width)
    )
}