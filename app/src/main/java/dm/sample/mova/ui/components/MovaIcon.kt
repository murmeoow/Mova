package dm.sample.mova.ui.components

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import dm.sample.mova.R

@Composable
fun MovaIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_mova_logo_s88),
        tint = Color.Unspecified,
        contentDescription = "imgMovaIcon",
        modifier = modifier
    )
}
