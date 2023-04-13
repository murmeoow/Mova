package dm.sample.mova.ui.screens.downloads

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.domain.entities.DownloadedMovie
import dm.sample.mova.ui.components.MovaTwoButtons
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.downloads.DownloadedMovie
import dm.sample.mova.ui.theme.Error
import dm.sample.mova.ui.theme.Grayscale300
import dm.sample.mova.ui.theme.Grayscale800
import dm.sample.mova.ui.utils.isDarkTheme

@Composable
fun DownloadBottomSheet(
    downloadedMovie: DownloadedMovie,
    onCancelClick: () -> Unit,
    onSubmitClick: (DownloadedMovie) -> Unit,
) {
    val isDarkTheme = isDarkTheme()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp, 12.dp)
                .padding(top = 8.dp)
                .background(Grayscale300, shape = RoundedCornerShape(10.dp)),
        )
        MovaVerticalSpacer(height = 24.dp)
        Text(
            text = stringResource(R.string.downloads_delete_title),
            style = MaterialTheme.typography.h4,
            color = Error
        )
        MovaVerticalSpacer(height = 22.dp)
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
        )
        MovaVerticalSpacer(height = 18.dp)
        Text(
            text = stringResource(R.string.downloads_delete_subtitle),
            style = MaterialTheme.typography.h5.copy(
                lineHeight = 24.sp
            ),
            textAlign = TextAlign.Center,
            color = if (isDarkTheme) MaterialTheme.colors.onBackground else Grayscale800
        )
        MovaVerticalSpacer(height = 18.dp)

        DownloadedMovie(
            downloadedMovie = downloadedMovie,
            isDownloaded = false,
            onDeleteClick = {},
            hasDeleteButton = false,
            modifier = Modifier.wrapContentWidth()
        )

        MovaVerticalSpacer(height = 24.dp)
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
        )
        MovaVerticalSpacer(height = 24.dp)
        MovaTwoButtons(
            firstText = R.string.cancel_text,
            secondText = R.string.downloads_delete_btn,
            onFirstClick = onCancelClick,
            onSecondClick = { onSubmitClick(downloadedMovie) }
        )
        MovaVerticalSpacer(height = 22.dp)
    }
}

@Preview
@Composable
fun DownLoadBottomSheetContent() {
    DownloadBottomSheet(
        downloadedMovie = DownloadedMovie(
            id = null,
            name = "Avatar",
            posterPath = "",
            0,0,0
        ),
        onCancelClick = { },
        onSubmitClick = {}
    )
}