package dm.sample.mova.ui.screens.moviedetails.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.domain.entities.MovieTrailersResult
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.utils.movaShimmer
import coil.compose.SubcomposeAsyncImage

@Composable
fun TabTrailerContent(
    trailersList: List<MovieTrailersResult>?,
    onVideoClick: (String) -> Unit,
    modifier: Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.background(MaterialTheme.colors.background)
    ) {
        if (trailersList.isNullOrEmpty()) {
            Image(
                painter = painterResource(R.drawable.not_found_image),
                contentDescription = "Not found 404 wallpaper",
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.5.dp),
                contentPadding = PaddingValues(24.dp),
                modifier = modifier
            ) {
                items(trailersList.size) { index ->
                    TrailersItem(
                        trailer = trailersList[index],
                        onVideoClick = {
                            onVideoClick(trailersList[index].key)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TrailersItem(
    trailer: MovieTrailersResult,
    onVideoClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(112.5.dp)
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(150.dp, 112.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable(
                    onClick = { onVideoClick() },
                    indication = null,
                    interactionSource = remember { NoRippleInteractionSource() }
                )
        ) {
            SubcomposeAsyncImage(
                model = trailer.youtubeThumbnailUrl(),
                loading = {
                    Box(modifier = Modifier.fillMaxSize().movaShimmer())
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp)),
                contentDescription = "Trailer thumbnail",
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_circle_play),
                tint = White,
                contentDescription = "play trailer",
                modifier = Modifier.size(20.dp),
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .clickable(
                    onClick = { onVideoClick() },
                    indication = null,
                    interactionSource = remember { NoRippleInteractionSource() }
                )
        ) {
            Text(
                text = trailer.name,
                style = MaterialTheme.typography.h6.copy(lineHeight = 21.6.sp),
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}