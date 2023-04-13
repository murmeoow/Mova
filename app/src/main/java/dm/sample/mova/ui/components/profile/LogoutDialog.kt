package dm.sample.mova.ui.components.profile

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.MovaTwoButtons
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Error
import dm.sample.mova.ui.theme.Grayscale300
import dm.sample.mova.ui.theme.Grayscale800
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun LogoutDialog(
    onClickLogout: () -> Unit,
    onClickCancel: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp, 12.dp)
                .padding(top = 8.dp)
                .background(Grayscale300, shape = RoundedCornerShape(10.dp)),
        )
        MovaVerticalSpacer(height = 24.dp)
        Text(
            text = stringResource(R.string.profile_logout),
            style = MaterialTheme.typography.h4,
            color = Error,
        )
        MovaVerticalSpacer(height = 24.dp)
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        MovaVerticalSpacer(height = 24.dp)
        Text(
            text = stringResource(R.string.profile_logout_dialog_message),
            style = MaterialTheme.typography.h5,
            color = if (isDarkTheme()) White else Grayscale800,
            textAlign = TextAlign.Center,
        )
        MovaVerticalSpacer(height = 24.dp)
        MovaTwoButtons(
            firstText = R.string.profile_logout_dialog_cancel,
            secondText = R.string.profile_logout_dialog_submit,
            onFirstClick = onClickCancel,
            onSecondClick = onClickLogout,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        MovaVerticalSpacer(height = 48.dp)
    }
}


@ShowkaseComposable(
    name = "Logout Bottom Sheet",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewLogoutBottomSheet() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        LogoutDialog(
            onClickLogout = {},
            onClickCancel = {},
        )
    }
}