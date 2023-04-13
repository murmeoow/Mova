package dm.sample.mova.ui.components.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Grayscale700
import dm.sample.mova.ui.theme.bodyXSmallRegular
import dm.sample.mova.ui.utils.movaShimmer
import coil.compose.SubcomposeAsyncImage
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import java.time.LocalDate

@Composable
fun NotificationMovieItem(
    modifier: Modifier = Modifier,
    movie: Movie?,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    if (isLoading) {
        NotificationItemShimmer(
            modifier = modifier,
            shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)
        )
    } else if (movie != null) {
        NotificationItemContent(
            modifier = modifier,
            movie = movie,
            onClick = onClick,
        )
    }
}

@Composable
private fun NotificationItemContent(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: () -> Unit,
) {
    val isPreview = LocalInspectionMode.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { NoRippleInteractionSource() }
            )
            .height(248.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (isPreview) {
            Image(
                painter = painterResource(R.drawable.ic_mova_logo_s88),
                contentDescription = "Movie poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 186.dp, height = 248.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )
        } else {
            SubcomposeAsyncImage(
                model = movie.posterImageUrl(),
                contentDescription = "Movie poster",
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier
                        .fillMaxSize()
                        .movaShimmer(),
                    )
                },
                modifier = Modifier
                    .size(width = 186.dp, height = 248.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )
        }

        Text(
            text = movie.title,
            style = MaterialTheme.typography.h6.copy(fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 12.dp)
                .weight(1f),
            overflow = TextOverflow.Ellipsis,
        )

        val date = movie.releaseDate?.let {
            "%02d/%02d/%d".format(it.dayOfMonth, it.monthValue, it.year)
        } ?: ""
        Text(text = date, style = bodyXSmallRegular, color = Grayscale700)
    }
}

@Composable
private fun NotificationItemShimmer(
    modifier: Modifier = Modifier,
    shimmer: Shimmer,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(248.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .size(width = 186.dp, height = 248.dp)
                .clip(RoundedCornerShape(10.dp))
                .movaShimmer(shimmer),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 12.dp)
                .height(40.dp)
                .weight(1f)
                .movaShimmer(shimmer),
        )

    }
}


@ShowkaseComposable(
    name = "Notification Movie Item",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(showBackground = true)
@Composable
fun PreviewNotificationMovieItem() {
    MovaTheme {
        NotificationMovieItem(
            movie = Movie(
                adult = true,
                backdropPath = "",
                id = 1,
                originalLanguage = "",
                originalTitle = "",
                overview = "",
                popularity = 1f,
                posterPath = "",
                releaseDate = LocalDate.now(),
                title = "Stranger Things",
                video = true,
                voteOverage = 10f,
                voteCount = 100f,
                genreIds = listOf()
            ),
            isLoading = false,
            onClick = {},
        )
    }
}


@ShowkaseComposable(
    name = "Notification Movie Item Loading",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(showBackground = true)
@Composable
fun PreviewLoadingNotificationMovieItem() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        NotificationMovieItem(
            movie = null,
            isLoading = true,
            onClick = {},
        )
    }
}