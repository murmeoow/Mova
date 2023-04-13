package dm.sample.mova.ui.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.profile.OptionToggleItem
import dm.sample.mova.ui.theme.MovaTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable


@Composable
fun NotificationSettingsScreen(
    navigateOnBack: () -> Unit,
) {
    Column(Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {

        MovaVerticalSpacer(height = 14.dp)
        HeaderWithButtonAndTitle(
            title = R.string.settings_notification_title,
            onBackClick = navigateOnBack,
        )
        MovaVerticalSpacer(height = 14.dp)

        OptionToggleItem(
            optionText = stringResource(R.string.settings_notification_general),
            isChecked = true,
            onToggleCheckChanged = {

            },
        )
        OptionToggleItem(
            optionText = stringResource(R.string.settings_notification_new_arrival),
            isChecked = false,
            onToggleCheckChanged = {

            },
        )
        OptionToggleItem(
            optionText = stringResource(R.string.settings_notification_new_services_available),
            isChecked = false,
            onToggleCheckChanged = {

            },
        )
        OptionToggleItem(
            optionText = stringResource(R.string.settings_notification_new_release_movie),
            isChecked = true,
            onToggleCheckChanged = {

            },
        )
        OptionToggleItem(
            optionText = stringResource(R.string.settings_notification_app_updates),
            isChecked = true,
            onToggleCheckChanged = {

            },
        )
        OptionToggleItem(
            optionText = stringResource(R.string.settings_notification_subscription),
            isChecked = false,
            onToggleCheckChanged = {

            },
        )
    }
}


@ShowkaseComposable(
    name = "Notification Settings Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSettingsNotificationScreen() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        NotificationSettingsScreen(
            navigateOnBack = {

            },
        )
    }
}