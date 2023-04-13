package dm.sample.mova.ui.components.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.utils.isDarkTheme
import dm.sample.mova.ui.components.profile.OptionItem
import dm.sample.mova.ui.components.profile.OptionRedItem
import dm.sample.mova.ui.components.profile.OptionToggleItem

@Composable
fun ProfileOptions(
    currentLanguageValue: String,
    hasSubscription: Boolean,
    onProfileEdit: () -> Unit,
    onClickNotificationOption: () -> Unit,
    onClickHelpCenterOption: () -> Unit,
    onClickDownloadOption: () -> Unit,
    onClickSecurityOption: () -> Unit,
    onClickLanguageOption: () -> Unit,
    onClickPrivacyPolicyOption: () -> Unit,
    onClickProfileLogoutOption: () -> Unit,
    onClickSubscriptionOption: () -> Unit,
    onThemeChange: (isDark: Boolean) -> Unit,
) {
    MovaVerticalSpacer(height = 10.dp)

    OptionItem(
        icon = painterResource(R.drawable.ic_person),
        optionText = stringResource(R.string.profile_edit),
        onClick = onProfileEdit
    )

    if (hasSubscription) OptionItem(
        icon = painterResource(R.drawable.ic_subscription),
        optionText = stringResource(R.string.profile_subscription),
        onClick = onClickSubscriptionOption
    )

    OptionItem(
        icon = painterResource(R.drawable.ic_notification),
        optionText = stringResource(R.string.profile_notification),
        onClick = onClickNotificationOption
    )

    OptionItem(
        icon = painterResource(R.drawable.ic_download),
        optionText = stringResource(R.string.profile_download),
        onClick = onClickDownloadOption,
    )

    OptionItem(
        icon = painterResource(R.drawable.ic_security),
        optionText = stringResource(R.string.profile_security),
        onClick = onClickSecurityOption,
    )

    OptionItem(
        icon = painterResource(R.drawable.ic_language),
        optionText = stringResource(R.string.profile_language),
        onClick = onClickLanguageOption,
        value = currentLanguageValue
    )

    OptionToggleItem(
        icon = painterResource(R.drawable.ic_outlined_show),
        optionText = stringResource(R.string.profile_dark_mode),
        onToggleCheckChanged = onThemeChange,
        isChecked = isDarkTheme(),
    )

    OptionItem(
        icon = painterResource(R.drawable.ic_info_square),
        optionText = stringResource(R.string.profile_help_center),
        onClick = onClickHelpCenterOption,
    )

    OptionItem(
        icon = painterResource(R.drawable.ic_privacy_policy),
        optionText = stringResource(R.string.profile_privacy_policy),
        onClick = onClickPrivacyPolicyOption
    )


    OptionRedItem(
        icon = painterResource(R.drawable.ic_logout_red),
        optionText = stringResource(R.string.profile_logout),
        onClick = onClickProfileLogoutOption,
        hasRightArrow = false
    )

    MovaVerticalSpacer(height = 55.dp)

}