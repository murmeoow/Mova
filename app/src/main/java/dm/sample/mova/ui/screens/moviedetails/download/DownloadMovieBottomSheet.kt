package dm.sample.mova.ui.screens.moviedetails.download

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.ui.components.MovaTwoButtons
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.theme.Error
import dm.sample.mova.ui.theme.Grayscale300
import dm.sample.mova.ui.theme.Grayscale800
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyXLargeSemiBold
import dm.sample.mova.ui.utils.isDarkTheme

@Composable
fun DownloadMovieBottomSheet(
    onSubmitClick: () -> Unit,
    onCancelClick: () -> Unit,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(38.dp, 12.dp)
                .padding(top = 8.dp)
                .background(Grayscale300, shape = RoundedCornerShape(10.dp)),
        )
        MovaVerticalSpacer(height = 24.dp)
        Text(
            text = stringResource(R.string.downloads_download),
            style = MaterialTheme.typography.h4,
            color = Error,
        )
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        )
        Text(
            text = stringResource(R.string.downloads_dialog),
            style = bodyXLargeSemiBold,
            color = if (isDarkTheme()) White else Grayscale800,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        MovaVerticalSpacer(height = 24.dp)
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        )
        MovaTwoButtons(
            firstText = R.string.downloads_dialog_cancel,
            secondText = R.string.downloads_dialog_submit,
            onFirstClick = onCancelClick,
            onSecondClick = onSubmitClick,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        MovaVerticalSpacer(height = 48.dp)
    }
}