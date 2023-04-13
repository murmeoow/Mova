package dm.sample.mova.ui.components.mylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.utils.isScrollingUp
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun MyListMovies(
    moviesList: List<Movie>,
    moviesInFavourite: List<Long>,
    onMovieClick: (Long) -> Unit,
    onFavouriteClick: (Long) -> Unit,
    onScrollToUp: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyGridState: LazyGridState = rememberLazyGridState()

    VerticalGridFavouriteMoviesList(
        movies = moviesList,
        moviesInFavourite = moviesInFavourite,
        state = lazyGridState,
        onMovieClick = onMovieClick,
        onFavouriteClick = onFavouriteClick,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )

    val isScrollingUp = lazyGridState.isScrollingUp()
    LaunchedEffect(isScrollingUp) {
        onScrollToUp(isScrollingUp)
    }
}


@Composable
fun MyListMoviesShimmer(
    onScrollToUp: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyGridState: LazyGridState = rememberLazyGridState()

    VerticalGridFavouriteMoviesListShimmer(
        state = lazyGridState,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )

    val isScrollingUp = lazyGridState.isScrollingUp()
    LaunchedEffect(isScrollingUp) {
        onScrollToUp(isScrollingUp)
    }
}

@Composable
private fun VerticalGridFavouriteMoviesList(
    movies: List<Movie>,
    moviesInFavourite: List<Long>,
    state: LazyGridState,
    onMovieClick: (Long) -> Unit,
    onFavouriteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = state,
        contentPadding = PaddingValues(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(movies.size) { index ->
            MovieCardWithFavourite(
                movie = movies[index],
                isInFavourite = moviesInFavourite.contains(movies[index].id),
                onFavouriteClick = { onFavouriteClick(movies[index].id) },
                modifier = Modifier
                    .clickable {
                        onMovieClick(movies[index].id)
                    }
                    .fillMaxWidth()
                    .height(248.dp)
            )
        }
    }
}


@Composable
private fun VerticalGridFavouriteMoviesListShimmer(
    state: LazyGridState,
    modifier: Modifier = Modifier,
) {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = state,
        contentPadding = PaddingValues(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(10) {
            MovieCardWithFavouriteShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(248.dp),
                shimmer = shimmer,
            )
        }
    }
}


@ShowkaseComposable(
    name = "My List Movies Shimmer",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(showBackground = true)
@Composable
fun PreviewMyListMoviesShimmer() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        MyListMoviesShimmer(
            onScrollToUp = { /* no-op */ },
            modifier = Modifier.fillMaxSize()
        )
    }
}