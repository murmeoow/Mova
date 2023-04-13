package dm.sample.mova.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.White
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun HomeTopBarButtons(
    onLogoClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    hasNotification: Boolean,
) {
    Row(
        Modifier
            .padding(horizontal = 24.dp, vertical = 40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(
            onClick = onLogoClick,
            interactionSource = remember { NoRippleInteractionSource() }
        ) {
            Image(
                painter = painterResource(R.drawable.ic_mova_logo),
                contentDescription = "Mova logo",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        IconButton(
            onClick = onSearchClick,
            interactionSource = remember { NoRippleInteractionSource() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                tint = White,
                contentDescription = "Search Icon",
                modifier = Modifier.size(28.dp)
            )
        }

        IconButton(
            onClick = onNotificationClick,
            interactionSource = remember { NoRippleInteractionSource() }
        ) {
            Image(
                painter = if (hasNotification) {
                    painterResource(R.drawable.ic_notification_red)
                } else {
                    painterResource(R.drawable.ic_notification)
                },
                contentDescription = "Search Icon",
                modifier = Modifier.size(28.dp),
            )
        }


    }
}

@ShowkaseComposable(
    name = "Home Top Bar Buttons",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(showBackground = true)
@Composable
fun PreviewHomeTopBarButtons() {
    MovaTheme() {
        Column(
            modifier = Modifier.background(Color.Black),
        ) {
            HomeTopBarButtons(
                onLogoClick = { },
                onSearchClick = { },
                onNotificationClick = { },
                hasNotification = true,
            )
        }
    }
}