package dm.sample.mova.ui.components.explorer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.ui.components.MovieCard
import dm.sample.mova.ui.components.MovieCardShimmer
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.utils.isScrollingUp
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun ExploreGridMovieList(
    movies: List<Movie>,
    onLoadMore: () -> Unit,
    onScrollToUp: (Boolean) -> Unit,
    onMovieClick: (Movie) -> Unit,
) {
    val lazyGridState: LazyGridState = rememberLazyGridState()

    val isScrollToEnd by remember {
        derivedStateOf {
            val lastVisibleItemIndex = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val lastItemIndex = lazyGridState.layoutInfo.totalItemsCount - 1
            lastVisibleItemIndex == lastItemIndex
        }
    }


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 20.dp),
        state = lazyGridState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(movies.size) { index ->
            MovieCard(movie = movies[index], modifier = Modifier.size(186.dp, 248.dp)) {
                onMovieClick(it)
            }
        }
    }


    LaunchedEffect(key1 = isScrollToEnd) {
        if (isScrollToEnd) {
            onLoadMore.invoke()
        }
    }

    val isScrollingUp = lazyGridState.isScrollingUp()
    LaunchedEffect(isScrollingUp) {
        onScrollToUp(isScrollingUp)
    }
}


@Composable
fun ExploreGridMovieListShimmer(
    onScrollToUp: (Boolean) -> Unit,
) {
    val lazyGridState: LazyGridState = rememberLazyGridState()


    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 24.dp),
        state = lazyGridState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(10) {
            MovieCardShimmer(shimmer = shimmer, modifier = Modifier.size(186.dp, 248.dp))
        }
    }

    val isScrollingUp = lazyGridState.isScrollingUp()
    LaunchedEffect(isScrollingUp) {
        onScrollToUp(isScrollingUp)
    }
}

@ShowkaseComposable(
    name = "Explore Grid Movie List Shimmer",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun PreviewExploreGridMovieListShimmer() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        ExploreGridMovieListShimmer(
            onScrollToUp = { /* no-op */ },
        )
    }
}