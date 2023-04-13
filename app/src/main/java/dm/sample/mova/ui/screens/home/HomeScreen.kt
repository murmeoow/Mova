package dm.sample.mova.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.MovieCategoryType
import dm.sample.mova.navigation.Screen
import dm.sample.mova.ui.components.MovaErrorScreen
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.home.HomeCategoryHeader
import dm.sample.mova.ui.components.home.HomeMovieCategoryItem
import dm.sample.mova.ui.components.home.HomeTopBarButtons
import dm.sample.mova.ui.components.home.RecommendedMovieHeader
import dm.sample.mova.ui.viewmodel.home.HomeMovieCategoryUI
import dm.sample.mova.ui.viewmodel.home.HomeUiError
import dm.sample.mova.ui.viewmodel.home.HomeUiState
import dm.sample.mova.ui.viewmodel.home.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit,
    onShowSnackBar: (isVisible: Boolean, text: String) -> Unit,
) {
    val context = LocalContext.current

    // Observer ui state
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = state.isAddInFavouriteError) {
        onShowSnackBar(
            state.isAddInFavouriteError,
            context.resources.getString(R.string.home_add_in_favourite_error)
        )
    }

    when {
        state.isLoading -> {
            MovaLoadingScreen()
        }
        state.error != null -> {
            MovaErrorScreen(
                isNetworkError = state.error == HomeUiError.NetworkError,
                onDismissClick = viewModel::fetchRecommendedMovie,
                onTryAgainClick = viewModel::fetchRecommendedMovie
            )
        }
        else -> {
            HomeContent(
                state = state,
                onPlayMovie = { /* TODO() */ },
                onAddMyList = viewModel::onAddMyList,
                onLogoClick = { /* TODO() */ },
                onSearchClick = { navigateTo(Screen.Explore.passArgument()) },
                onNotificationClick = { navigateTo(Screen.Notification.route) },
                onClickSeeAll = { navigateTo(Screen.ActionMenu.passArgument(it.id)) },
                onMovieSelect = { navigateTo(Screen.MovieDetails.passArgument(it.id)) },
                onClickTryAgain = { category ->
                    when (category) {
                        MovieCategoryType.NowPlaying -> viewModel.fetchNowPlayingMovies()
                        MovieCategoryType.Popular -> viewModel.fetchPopularMovies()
                        MovieCategoryType.TopRated -> viewModel.fetchTopRatedMovies()
                        MovieCategoryType.Upcoming -> viewModel.fetchUpcomingMovies()
                        MovieCategoryType.Unknown -> { /* no-op */ }
                    }
                },
                onRecommendedMovieClick = { navigateTo(Screen.MovieDetails.passArgument(it)) }
            )
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    onPlayMovie: (Movie) -> Unit,
    onAddMyList: (Movie) -> Unit,
    onLogoClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onClickSeeAll: (HomeMovieCategoryUI) -> Unit,
    onMovieSelect: (Movie) -> Unit,
    onClickTryAgain: (MovieCategoryType) -> Unit,
    onRecommendedMovieClick: (Long) -> Unit
) {
    HomeContentLayout(
        homeHeader = {
            RecommendedMovieHeader(
                isInFavourite = state.isRecommendedMovieInFavourite,
                onPlayMovie = onPlayMovie,
                onAddMyList = onAddMyList,
                movie = state.recommendedMovie,
                hasAddMyListButton = state.isGuestAccount.not(),
                onClick = { onRecommendedMovieClick(state.recommendedMovie?.id ?: 0L) }
            )
            HomeTopBarButtons(
                onLogoClick = onLogoClick,
                onSearchClick = onSearchClick,
                onNotificationClick = onNotificationClick,
                hasNotification = state.hasNotification
            )
        },
        nowPlayingMoviesItemHeader = {
            HomeCategoryHeader(
                categoryName = state.nowPlayingMovies.name,
                onClickSeeAll = { onClickSeeAll(state.nowPlayingMovies) },
            )
        },
        nowPlayingMoviesItem = {
            HomeMovieCategoryItem(
                category = state.nowPlayingMovies,
                onMovieSelect = onMovieSelect,
                onTryAgainClick = { onClickTryAgain(MovieCategoryType.NowPlaying) },
            )
        },
        popularMoviesItemHeader = {
            HomeCategoryHeader(
                categoryName = state.popularMovies.name,
                onClickSeeAll = { onClickSeeAll(state.popularMovies) },
            )
        },
        popularMoviesItem = {
            HomeMovieCategoryItem(
                category = state.popularMovies,
                onMovieSelect = onMovieSelect,
                onTryAgainClick = { onClickTryAgain(MovieCategoryType.Popular) },
            )
        },
        upcomingMoviesItemHeader = {
            HomeCategoryHeader(
                categoryName = state.upcomingMovies.name,
                onClickSeeAll = { onClickSeeAll(state.upcomingMovies) },
            )
        },
        upcomingMoviesItem = {
            HomeMovieCategoryItem(
                category = state.upcomingMovies,
                onMovieSelect = onMovieSelect,
                onTryAgainClick = { onClickTryAgain(MovieCategoryType.Upcoming) },
            )
        },
        topRatedMoviesItemHeader = {
            HomeCategoryHeader(
                categoryName = state.topRatedMovies.name,
                onClickSeeAll = { onClickSeeAll(state.topRatedMovies) },
            )
        },
        topRatedMoviesItem = {
            HomeMovieCategoryItem(
                category = state.topRatedMovies,
                onMovieSelect = onMovieSelect,
                onTryAgainClick = { onClickTryAgain(MovieCategoryType.TopRated) },
            )
        },
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeContentLayout(
    homeHeader: @Composable () -> Unit,
    nowPlayingMoviesItemHeader: @Composable () -> Unit,
    nowPlayingMoviesItem: @Composable () -> Unit,
    popularMoviesItemHeader: @Composable () -> Unit,
    popularMoviesItem: @Composable () -> Unit,
    upcomingMoviesItemHeader: @Composable () -> Unit,
    upcomingMoviesItem: @Composable () -> Unit,
    topRatedMoviesItemHeader: @Composable () -> Unit,
    topRatedMoviesItem: @Composable () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    var blur by remember {
        mutableStateOf(0f)
    }

    LazyColumn(state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentPadding = PaddingValues(bottom = 20.dp)) {

        item {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY * 0.5f
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                        blur = previousOffset * 0.004f
                    }
                    .blur(radius = blur.dp),
                content = {
                    homeHeader()
                },
            )
        }

        stickyHeader {
            popularMoviesItemHeader()
        }
        item {
            popularMoviesItem()
        }

        stickyHeader {
            topRatedMoviesItemHeader()
        }
        item {
            topRatedMoviesItem()
        }

        stickyHeader {
            nowPlayingMoviesItemHeader()
        }
        item {
            nowPlayingMoviesItem()
        }
        
        stickyHeader {
            upcomingMoviesItemHeader()
        }
        item {
            upcomingMoviesItem()
        }

        item { // add bottom space for bottom navigation
            MovaVerticalSpacer(height = 50.dp)
        }

    }
}
