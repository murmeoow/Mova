package dm.sample.mova.ui.components.actionview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.ui.components.MovieCard

@Composable
fun VerticalGridMoviesList(
    movies: List<Movie>,
    state: LazyGridState,
    contentPadding: PaddingValues,
    onMovieClick: (Long) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isScrollToEnd by remember {
        derivedStateOf {
            val lastVisibleItemIndex = state.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val lastItemIndex = state.layoutInfo.totalItemsCount - 1
            lastVisibleItemIndex == lastItemIndex
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = state,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(MaterialTheme.colors.background)
    ) {
        items(movies.size) { index ->
            MovieCard(
                movie = movies[index],
                modifier = Modifier.height(248.dp).fillMaxWidth(),
                onClick = {
                    onMovieClick(it.id)
                }
            )
        }
    }

    LaunchedEffect(key1 = isScrollToEnd) {
        if (isScrollToEnd) {
            onLoadMore.invoke()
        }
    }
}
