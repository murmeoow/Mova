package dm.sample.mova.ui.components.mylist

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.bodyXSmallSemiBold
import dm.sample.mova.ui.utils.movaShimmer
import coil.compose.rememberAsyncImagePainter
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun MovieCardWithFavourite(
    movie: Movie,
    isInFavourite: Boolean,
    onFavouriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteIcon = if (isInFavourite) {
        R.drawable.ic_filled_heart
    } else {
        R.drawable.ic_outlined_heart
    }
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(movie.posterImageUrl()),
                contentDescription = "Movie card cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Card(
                shape = RoundedCornerShape(6.dp),
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp)
                    .height(24.dp),
            ) {
                Text(
                    text = "%.1f".format(movie.voteOverage),
                    style = bodyXSmallSemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                )
            }
            IconButton(
                onClick = {
                    onFavouriteClick.invoke()
                },
                interactionSource =  remember { NoRippleInteractionSource() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 12.dp),
            ) {
                Icon(
                    painter = painterResource(id = favoriteIcon),
                    tint = Color.Unspecified,
                    contentDescription = "add to favourite list"
                )
            }
        }
    }
}


@Composable
fun MovieCardWithFavouriteShimmer(
    shimmer: Shimmer,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .movaShimmer(customShimmer = shimmer)
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 12.dp),
                onClick = {},
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_outlined_heart),
                    tint = Color.Unspecified,
                    contentDescription = "add to favourite list"
                )
            }
        }
    }
}


@ShowkaseComposable(
    name = "Movie Card With Favorite Shimmer",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
    heightDp = 248,
    widthDp = 184
)
@Preview(heightDp = 248, widthDp = 184)
@Composable
fun PreviewMovieCardWithFavouriteShimmer() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        MovieCardWithFavouriteShimmer(
            shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window),
            modifier = Modifier.fillMaxSize(),
        )
    }
}