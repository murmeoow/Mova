package dm.sample.mova.ui.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaTransparentRedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.profile.OptionItem
import dm.sample.mova.ui.components.profile.OptionToggleItem
import dm.sample.mova.ui.theme.MovaTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable


@Composable
fun SecuritySettingsScreen(
    navigateOnBack: () -> Unit,
    onChangePin: () -> Unit,
    onChangePassword: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        MovaVerticalSpacer(height = 14.dp)
        HeaderWithButtonAndTitle(
            title = R.string.settings_security_title,
            onBackClick = navigateOnBack,
        )
        MovaVerticalSpacer(height = 14.dp)

        Column(modifier = Modifier.verticalScroll(scrollState)) {

            Text(
                text = stringResource(R.string.settings_security_control),
                style = MaterialTheme.typography.h5.copy(fontSize = 20.sp),
                modifier = Modifier.padding(start = 24.dp, bottom = 14.dp, top = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )

            OptionItem(
                optionText = stringResource(R.string.settings_security_alerts),
                onClick = { },
            )

            OptionItem(
                optionText = stringResource(R.string.settings_security_manage_device),
                onClick = { },
            )

            OptionItem(
                optionText = stringResource(R.string.settings_security_manage_permission),
                onClick = { },
            )

            Text(
                text = stringResource(R.string.settings_security),
                style = MaterialTheme.typography.h5.copy(fontSize = 20.sp),
                modifier = Modifier.padding(start = 24.dp, bottom = 14.dp, top = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )

            OptionToggleItem(
                optionText = stringResource(R.string.settings_security_remember_me),
                isChecked = true,
                onToggleCheckChanged = {

                }
            )

            OptionToggleItem(
                optionText = stringResource(R.string.settings_security_face_id),
                isChecked = false,
                onToggleCheckChanged = {

                }
            )

            OptionToggleItem(
                optionText = stringResource(R.string.settings_security_biometric_id),
                isChecked = true,
                onToggleCheckChanged = {

                }
            )

            OptionItem(
                optionText = stringResource(R.string.settings_security_google_auth),
                onClick = { },
            )

            MovaVerticalSpacer(height = 14.dp)

            MovaTransparentRedButton(
                buttonText = R.string.settings_security_change_pin,
                onClick = onChangePin,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            MovaVerticalSpacer(height = 24.dp)

            MovaTransparentRedButton(
                buttonText = R.string.settings_security_change_password,
                onClick = onChangePassword,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            MovaVerticalSpacer(height = 70.dp)
        }

    }
}

@ShowkaseComposable(
    name = "Security Settings Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSecuritySettingsScreen() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        SecuritySettingsScreen(
            navigateOnBack = {

            },
            onChangePin = {

            },
            onChangePassword = {

            }
        )
    }
}