package dm.sample.mova.ui.components.downloads

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.domain.entities.DownloadedMovie
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.TransparentRed
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyMediumSemiBold
import dm.sample.mova.ui.theme.bodyXSmallSemiBold
import dm.sample.mova.ui.utils.movaShimmer
import coil.compose.SubcomposeAsyncImage

@Composable
fun DownloadedMovie(
    modifier: Modifier,
    downloadedMovie: DownloadedMovie,
    isDownloaded: Boolean,
    hasDeleteButton: Boolean = true,
    onVideoClick: (String) -> Unit = {},
    onDeleteClick: (DownloadedMovie) -> Unit,
) {
    Row(
        modifier = modifier
            .height(113.dp)
            .then(if (isDownloaded) Modifier.movaShimmer() else Modifier)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(10.dp))
                .clickable(
                    interactionSource = NoRippleInteractionSource(),
                    indication = null,
                    enabled = isDownloaded.not(),
                    onClick = { onVideoClick(downloadedMovie.name) }
                )
        ) {
            SubcomposeAsyncImage(
                model = downloadedMovie.posterPath,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .movaShimmer(),
                    )
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(150.dp),
                contentDescription = "Movie card cover",
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_circle_play),
                tint = White,
                contentDescription = "play trailer",
                modifier = Modifier.size(20.dp),
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .then(if (hasDeleteButton) Modifier.fillMaxWidth() else Modifier.wrapContentWidth())
                .padding(start = 20.dp)
        ) {
            Text(
                text = downloadedMovie.name,
                style = MaterialTheme.typography.h6.copy(
                    lineHeight = 21.6.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = stringResource(id = R.string.downloads_duration),
                style = bodyMediumSemiBold.copy(
                    lineHeight = 19.6.sp
                ),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(top = 12.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                .padding(top = 12.dp)
            ) {
                Box(modifier = Modifier
                    .background(TransparentRed, RoundedCornerShape(6.dp))

                ) {
                    Text(
                        text = stringResource(id = R.string.downloads_size),
                        style = bodyXSmallSemiBold.copy(
                            lineHeight = 12.sp
                        ),
                        color = Primary500,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                    )
                }
                if (hasDeleteButton) {
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { onDeleteClick(downloadedMovie) },
                        interactionSource = remember { NoRippleInteractionSource() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            tint = Color.Unspecified,
                            contentDescription = "Delete video button",
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DownloadedMoviePreview() {
    DownloadedMovie(
        Modifier,
        DownloadedMovie(null, "Avatar", "", 0, 0, 0),
        false,
        false,
        {  }, { }
    )
}