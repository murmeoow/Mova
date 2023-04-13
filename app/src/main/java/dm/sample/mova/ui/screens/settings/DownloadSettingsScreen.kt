package dm.sample.mova.ui.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.profile.OptionItem
import dm.sample.mova.ui.components.profile.OptionToggleItem
import dm.sample.mova.ui.theme.MovaTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun DownloadSettingsScreen(
    navigateOnBack: () -> Unit,
) {
    Column(Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {

        MovaVerticalSpacer(height = 14.dp)
        HeaderWithButtonAndTitle(
            title = R.string.settings_download_title,
            onBackClick = navigateOnBack,
        )
        MovaVerticalSpacer(height = 14.dp)

        OptionToggleItem(
            optionText = stringResource(R.string.settings_download_wifi_only),
            isChecked = true,
            icon = painterResource(R.drawable.ic_wifi),
            onToggleCheckChanged = { },
        )

        OptionItem(
            icon = painterResource(R.drawable.ic_download),
            optionText = stringResource(R.string.settings_download_smart_download),
            onClick = {  },
        )

        OptionItem(
            icon = painterResource(R.drawable.ic_video),
            optionText = stringResource(R.string.settings_download_video_quality),
            onClick = {  },
        )

        OptionItem(
            icon = painterResource(R.drawable.ic_voice),
            optionText = stringResource(R.string.settings_download_voice_quality),
            onClick = {  },
        )

        OptionItem(
            icon = painterResource(R.drawable.ic_delete),
            optionText = stringResource(R.string.settings_download_delete_all_downloads),
            hasRightArrow = false,
            onClick = {  },
        )

        OptionItem(
            icon = painterResource(R.drawable.ic_delete),
            optionText = stringResource(R.string.settings_download_delete_cache),
            hasRightArrow = false,
            onClick = {  },
        )
    }
}

@ShowkaseComposable(
    name = "Download Settings Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewDownloadSettingsScreen() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        DownloadSettingsScreen(
            navigateOnBack = {

            }
        )
    }
}