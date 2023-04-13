package dm.sample.mova.ui.screens.moviedetails.download

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dm.sample.mova.R
import dm.sample.mova.ui.components.MovaTransparentRedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.downloads.MovaLinearProgressIndicator
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Dark2
import dm.sample.mova.ui.theme.Dark3
import dm.sample.mova.ui.theme.Grayscale300
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyLargeRegular
import dm.sample.mova.ui.theme.bodyMediumBold
import dm.sample.mova.ui.utils.isDarkTheme
import kotlin.math.abs

@Composable
fun DownloadMovieDialog(
    progress: Int,
    size: Float,
    sizeLeft: Float,
    onHide: () -> Unit,
    onCancel: () -> Unit
) {
    val isDarkTheme = isDarkTheme()

    Dialog(onDismissRequest = { onHide() }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(340.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(if (isDarkTheme) Dark2 else White)
                .padding(horizontal = 24.dp)
        ) {
            MovaVerticalSpacer(height = 32.dp)
            Text(
                text = stringResource(id = R.string.downloads_download),
                color = Primary500,
                style = MaterialTheme.typography.h4.copy(
                    lineHeight = 28.8.sp
                )
            )
            MovaVerticalSpacer(height = 12.dp)
            Text(
                text = stringResource(id = R.string.downloads_is_loading),
                style = bodyLargeRegular.copy(
                    lineHeight = 24.4.sp
                ),
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            MovaVerticalSpacer(height = 24.dp)
            Divider(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxWidth()
            )
            MovaVerticalSpacer(height = 30.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
            ) {
                Text(
                    text = stringResource(R.string.downloads_progress, "%.1f".format(sizeLeft), "%.1f".format(abs(size))),
                    style = bodyMediumBold.copy(
                        lineHeight = 19.6.sp
                    ),
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(id = R.string.downloads_progress_percentage, progress.toString()),
                    style = bodyMediumBold.copy(
                        lineHeight = 19.6.sp
                    ),
                    color = Primary500,
                )
            }
            MovaVerticalSpacer(height = 8.dp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                MovaLinearProgressIndicator(
                    progress = progress.toFloat() / 100,
                    color = Primary500,
                    backgroundColor = if (isDarkTheme) Dark3 else Grayscale300,
                    modifier = Modifier
                        .padding(end = 3.dp)
                        .weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = "Stop downloading",
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = NoRippleInteractionSource(),
                            onClick = onCancel
                        )
                )
            }
            MovaVerticalSpacer(height = 28.dp)
            MovaTransparentRedButton(
                buttonText = R.string.downloads_hide,
                onClick = onHide,
                modifier = Modifier
            )
            MovaVerticalSpacer(height = 32.dp)
        }
    }
}

@Preview
@Composable
fun DownloadMovieDialogPrevie() {
    DownloadMovieDialog(
        progress = 45,
        size = 458.4f,
        sizeLeft = 986.6f,
        onHide = {},
        onCancel = {}
    )
}