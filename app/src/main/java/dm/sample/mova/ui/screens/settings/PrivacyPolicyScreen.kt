package dm.sample.mova.ui.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.bodyMediumRegular
import com.airbnb.android.showkase.annotation.ShowkaseComposable


@Composable
fun PrivacyPolicyScreen(
    navigateOnBack: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {

        HeaderWithButtonAndTitle(
            title = R.string.settings_privacy_title,
            onBackClick = navigateOnBack,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
        ) {

            Text(
                text = stringResource(R.string.settings_privacy_first_message),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )


            Text(
                text = stringResource(R.string.settings_privacy_information_collect),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )

            Text(
                text = stringResource(R.string.settings_privacy_information_collect_text),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )



            Text(
                text = stringResource(R.string.settings_privacy_information_sharing),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )


            Text(
                text = stringResource(R.string.settings_privacy_information_sharing_text),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )


            Text(
                text = stringResource(R.string.settings_privacy_information_protect),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )

            Text(
                text = stringResource(R.string.settings_privacy_information_protect_text),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )


            Text(
                text = stringResource(R.string.settings_privacy_third_party_products),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            Text(
                text = stringResource(R.string.settings_privacy_third_party_products_text),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )


            Text(
                text = stringResource(R.string.settings_privacy_children),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )

            Text(
                text = stringResource(R.string.settings_privacy_children_text),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )

            Text(
                text = stringResource(R.string.settings_privacy_changes),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )


            Text(
                text = stringResource(R.string.settings_privacy_changes_text),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )

            Text(
                text = stringResource(R.string.settings_privacy_internation_transfer),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )

            Text(
                text = stringResource(R.string.settings_privacy_internation_transfer_text),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )


            Text(
                text = stringResource(R.string.settings_privacy_information_control),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )

            Text(
                text = stringResource(R.string.settings_privacy_information_control),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )


            Text(
                text = stringResource(R.string.settings_privacy_retention),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )

            Text(
                text = stringResource(R.string.settings_privacy_retention_text),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )

            Text(
                text = stringResource(R.string.settings_privacy_choice),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )

            Text(
                text = stringResource(R.string.settings_privacy_choice_text),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )



            Text(
                text = stringResource(R.string.settings_privacy_california_rights),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )

            Text(
                text = stringResource(R.string.settings_privacy_california_rights_text),
                style = bodyMediumRegular,
                color = MaterialTheme.colors.onBackground,
            )


            MovaVerticalSpacer(height = 24.dp)

        }

    }
}


@ShowkaseComposable(
    name = "Privacy Policy Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS
)
@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewPrivacyPolicyScreen() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        PrivacyPolicyScreen(
            navigateOnBack = { },
        )
    }
}