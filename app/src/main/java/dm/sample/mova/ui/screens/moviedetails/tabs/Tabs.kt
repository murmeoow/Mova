package dm.sample.mova.ui.screens.moviedetails.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.MovieTrailersResult
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Grayscale500
import dm.sample.mova.ui.theme.Grayscale700
import dm.sample.mova.ui.theme.bodyLargeSemiBold
import dm.sample.mova.ui.utils.isDarkTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MovieDetailsPager(
    pagerState: PagerState,
    moviesList: List<Movie>?,
    trailersList: List<MovieTrailersResult>?,
    hasError: Boolean,
    isLoading: Boolean,
    onMovieClick: (Long) -> Unit,
    onTrailerClick: (String) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        count = 2,
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> TabTrailerContent(
                trailersList = trailersList,
                onVideoClick = onTrailerClick,
                modifier = modifier.fillMaxSize()
            )
            1 -> TabSimilarMoviesContent(
                moviesList = moviesList,
                hasError = hasError,
                isLoading = isLoading,
                onMovieClick = onMovieClick,
                onLoadMore = onLoadMore,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MovieDetailsTabs(
    pagerState: PagerState,
    fetchTabSimilarMovies: () -> Unit,
) {
    val tabData = listOf(
        R.string.movie_details_tab_trailers,
        R.string.movie_details_tab_like_this,
    )
    val scope = rememberCoroutineScope()
    val unselectedColor = if (isDarkTheme()) Grayscale700 else Grayscale500

    LaunchedEffect(key1 = pagerState.currentPage) {
        if (pagerState.currentPage == 1) {
            fetchTabSimilarMovies.invoke()
        }
    }

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        divider = {
            TabRowDefaults.Divider(
                thickness = 2.dp,
                color = MaterialTheme.colors.secondary
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 4.dp,
                color = MaterialTheme.colors.primary,
            )
        },
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .background(MaterialTheme.colors.background)
    ) {
        tabData.forEachIndexed { index, text ->
            val selected = pagerState.currentPage == index
            Tab(
                selectedContentColor = Color.Transparent,
                unselectedContentColor = Color.Transparent,
                selected = selected,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = stringResource(id = text),
                        style = bodyLargeSemiBold.copy(
                            lineHeight = 22.sp,
                            textAlign = TextAlign.Center
                        ),
                        color = if (selected) MaterialTheme.colors.primary else unselectedColor
                    )
                },
                interactionSource = remember { NoRippleInteractionSource() }
            )
        }
    }
}