package dm.sample.mova.ui.components.explorer.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.TransparentRed
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun FilterHeaderButton(
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(56.dp, 56.dp)
            .background(color = TransparentRed, shape = RoundedCornerShape(16.dp))
            .clickable(
                onClick = onClick,
                enabled = isEnabled,
                interactionSource = remember { NoRippleInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_filter_red),
            contentDescription = "Filter icon",
            tint = Primary500
        )
    }
}

@ShowkaseComposable(
    name = "Filter Button",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(showBackground = true)
@Composable
fun FilterButtonPreview() {
    MovaTheme {
        FilterHeaderButton(false) {

        }
    }
}