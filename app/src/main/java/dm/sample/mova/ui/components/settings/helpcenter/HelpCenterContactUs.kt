package dm.sample.mova.ui.components.settings.helpcenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.ui.components.settings.helpcenter.ContactButton

@Composable
fun HelpCenterContactUs(
    onContactClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {

        ContactButton(
            text = stringResource(R.string.settings_help_customer_service),
            icon = painterResource(R.drawable.ic_support),
            onClick = onContactClick,
            modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
        )

        ContactButton(
            text = stringResource(R.string.settings_help_whatsapp),
            icon = painterResource(R.drawable.ic_whatsapp),
            onClick = onContactClick,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        ContactButton(
            text = stringResource(R.string.settings_help_website),
            icon = painterResource(R.drawable.ic_globus),
            onClick = onContactClick,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        ContactButton(
            text = stringResource(R.string.settings_help_facebook),
            icon = painterResource(R.drawable.ic_facebook_red),
            onClick = onContactClick,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        ContactButton(
            text = stringResource(R.string.settings_help_twitter),
            icon = painterResource(R.drawable.ic_twitter),
            onClick = onContactClick,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        ContactButton(
            text = stringResource(R.string.settings_help_instagram),
            icon = painterResource(R.drawable.ic_instagram),
            onClick = onContactClick,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}