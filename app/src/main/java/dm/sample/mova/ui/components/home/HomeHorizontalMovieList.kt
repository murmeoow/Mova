package dm.sample.mova.ui.components.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.ui.components.MovieCard
import dm.sample.mova.ui.components.MovieCardShimmer
import dm.sample.mova.ui.theme.MovaTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun HomeHorizontalMovieList(movies: List<Movie>, onMovieSelect: (Movie) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        modifier = Modifier
            .background(MaterialTheme.colors.background),
    ) {
        items(movies.size) { index ->
            MovieCard(
                movie = movies[index],
                modifier = Modifier.size(150.dp, 200.dp),
                onClick = onMovieSelect
            )
        }
    }
}


@Composable
fun HomeHorizontalMovieListShimmer(
    modifier: Modifier = Modifier,
) {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(top = 14.dp),
    ) {
        items(10) {
            MovieCardShimmer(modifier = Modifier.size(150.dp, 200.dp), shimmer = shimmer)
        }
    }
}

@ShowkaseComposable(
    name = "Home Horizontal Movie List Shimmer",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(heightDp = 300, uiMode = UI_MODE_NIGHT_YES)
@Preview(heightDp = 300)
@Composable
fun PreviewHomeHorizontalMovieListShimmer() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        HomeHorizontalMovieListShimmer(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(bottom = 14.dp)
        )
    }
}