package dm.sample.mova.ui.components.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Grayscale700
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodySmallMedium

@Composable
fun JoinPremiumContainer(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { NoRippleInteractionSource() }
            )
            .border(
                border = BorderStroke(2.dp, Primary500),
                shape = RoundedCornerShape(32.dp),
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_crown),
            contentDescription = "premium crown",
            modifier = Modifier.padding(vertical = 32.dp, horizontal = 25.dp)
        )

        Column(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.profile_screen_premium_title),
                style = MaterialTheme.typography.h4,
                color = Primary500,
            )
            MovaVerticalSpacer(height = 8.dp)
            Text(
                text = stringResource(R.string.profile_screen_premium_subtitle),
                style = bodySmallMedium,
                color = Grayscale700,
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "arrow to join premium",
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}