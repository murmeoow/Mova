package dm.sample.mova.ui.components.profile

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.SimpleHeader
import dm.sample.mova.ui.screens.auth.StartIcon
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyXLargeSemiBold
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun GuestScreen(
    modifier: Modifier = Modifier,
    hasBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (hasBackButton) {
            SimpleHeader(onBackClick = onBackClick!!)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
        ) {
            StartIcon()
            MovaVerticalSpacer(12.dp)
            Text(
                text = stringResource(R.string.guest_screen_message),
                style = bodyXLargeSemiBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
            )
            MovaVerticalSpacer(24.dp)
            SignContainer(
                onClick = onClick,
            )
        }
    }
}

@Composable
private fun SignContainer(modifier: Modifier = Modifier, onClick: () -> Unit) {
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
            modifier = Modifier.padding(vertical = 35.dp, horizontal = 25.dp)
        )
        Column(
            Modifier
                .padding(vertical = 24.dp)
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.guest_screen_title),
                style = MaterialTheme.typography.h4,
                color = Primary500,
                overflow = TextOverflow.Ellipsis,
            )
            MovaVerticalSpacer(height = 8.dp)
        }
        Image(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "arrow to join premium",
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}


@ShowkaseComposable(
    name = "Guest Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewGuestScreen() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
            GuestScreen(onClick = {})
        }
    }
}