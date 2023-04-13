package dm.sample.mova.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Primary300
import dm.sample.mova.ui.theme.bodyXSmallSemiBold
import dm.sample.mova.ui.utils.movaShimmer
import coil.compose.SubcomposeAsyncImage
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import java.time.LocalDate

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClick: (Movie) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.clickable(
            onClick = { onClick(movie) },
            indication = null,
            interactionSource = remember { NoRippleInteractionSource() }
        )
    ) {
        Box {
            SubcomposeAsyncImage(
                model = movie.posterImageUrl(),
                loading = {
                    Box(
                        modifier = Modifier
                        .fillMaxSize()
                        .movaShimmer(),
                    )
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Movie card cover",
            )
            Card(
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp)
                    .height(24.dp),
                shape = RoundedCornerShape(6.dp),
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Text(
                    text = "%.1f".format(movie.voteOverage),
                    style = bodyXSmallSemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                )
            }
        }
    }
}


@Composable
fun MovieCardShimmer(
    modifier: Modifier = Modifier,
    shimmer: Shimmer,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.movaShimmer(shimmer),
        ) {
            Box(modifier = Modifier.fillMaxSize())
            Card(
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp)
                    .height(24.dp),
                shape = RoundedCornerShape(6.dp),
                backgroundColor = Primary300,
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                )
            }
        }
    }
}


@ShowkaseComposable(
    name = "Movie card",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(widthDp = 186, heightDp = 248)
@Composable
fun MovieCardPreview() {
    MovaTheme {
        MovieCard(
            Movie(
                adult = false,
                backdropPath = "",
                genreIds = emptyList(),
                id = 1,
                originalLanguage = "",
                originalTitle = "",
                overview = "",
                popularity = 1f,
                posterPath = "",
                releaseDate = LocalDate.now(),
                title = "",
                video = false,
                voteOverage = 7.1f,
                voteCount = 1400f,
            ),
            onClick = {},
        )
    }
}


@ShowkaseComposable(
    name = "Movie card Shimmer",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(widthDp = 186, heightDp = 248)
@Composable
fun MovieCardShimmerPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        val shimmer = rememberShimmer(ShimmerBounds.View)
        MovieCardShimmer(shimmer = shimmer)
    }
}