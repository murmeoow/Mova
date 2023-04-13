package dm.sample.mova.ui.screens.moviedetails

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Genre
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.MovieTrailersResult
import dm.sample.mova.ui.components.MovaErrorScreen
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.animation.HorizontalScrollAnimation
import dm.sample.mova.ui.components.moviedetails.BorderedText
import dm.sample.mova.ui.components.moviedetails.CastList
import dm.sample.mova.ui.components.moviedetails.DownloadButton
import dm.sample.mova.ui.components.moviedetails.MovieDetailsHeader
import dm.sample.mova.ui.components.moviedetails.MovieRating
import dm.sample.mova.ui.components.moviedetails.PlayButton
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.screens.moviedetails.download.DownloadMovieBottomSheet
import dm.sample.mova.ui.screens.moviedetails.download.DownloadMovieDialog
import dm.sample.mova.ui.screens.moviedetails.rating.GuestGiveRatingDialog
import dm.sample.mova.ui.screens.moviedetails.rating.RatingDialog
import dm.sample.mova.ui.screens.moviedetails.tabs.MovieDetailsPager
import dm.sample.mova.ui.screens.moviedetails.tabs.MovieDetailsTabs
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.bodyMediumSemiBold
import dm.sample.mova.ui.theme.bodySmallMedium
import dm.sample.mova.ui.utils.clickableSingle
import dm.sample.mova.ui.viewmodel.moviedetails.MovieDetailsNavEvent
import dm.sample.mova.ui.viewmodel.moviedetails.MovieDetailsUiAction
import dm.sample.mova.ui.viewmodel.moviedetails.MovieDetailsViewModel
import dm.sample.mova.ui.viewmodel.moviedetails.models.CastUiModel
import dm.sample.mova.ui.viewmodel.moviedetails.models.MovieDetailsUiModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dm.sample.mova.ui.screens.moviedetails.MovieDetailsBottomSheetsType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToTrailerWebView: (String) -> Unit,
    navigateToMovieDetails: (Long) -> Unit,
    navigateToStartScreen: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    // if you want to show dialog just set type
    var visibleDialog by remember { mutableStateOf(MovieDetailsBottomSheetsType.RATING) }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    val showBottomSheet = { scope.launch { bottomSheetState.show() } }
    val hideBottomSheet = { scope.launch { bottomSheetState.hide() } }

    LaunchedEffect(key1 = bottomSheetState.currentValue) {
        if (bottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
            viewModel.onAction(MovieDetailsUiAction.CloseDownloadDownloadPrompt)
        }
    }

    LaunchedEffect(key1 = state.isDownloadBottomSheetOpen) {
        if (state.isDownloadBottomSheetOpen) {
           visibleDialog = MovieDetailsBottomSheetsType.DOWNLOAD
            showBottomSheet()
        } else {
            viewModel.onAction(MovieDetailsUiAction.CloseDownloadDownloadPrompt)
            hideBottomSheet()
        }
    }

    when {
        state.isLoading -> MovaLoadingScreen()
        state.isError -> MovaErrorScreen(
            isNetworkError = state.isNetworkError,
            onDismissClick = onBackClick,
            onTryAgainClick = { viewModel.onAction(MovieDetailsUiAction.TryAgain) }
        )
        else -> {
            state.movie?.let {
                ModalBottomSheetLayout(
                    sheetState = bottomSheetState,
                    sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    sheetContent = {

                        when (visibleDialog) {
                            MovieDetailsBottomSheetsType.RATING -> {
                                RatingDialog(
                                    averageRating = it.averageRating,
                                    currentRating = state.userRating,
                                    usersCount = it.voteCount,
                                    onCancelClick = { hideBottomSheet() },
                                    onSubmitClick = {
                                        viewModel.onAction(MovieDetailsUiAction.RateMovie)
                                        hideBottomSheet()
                                    },
                                    onRatingClick = {
                                        viewModel.onAction(MovieDetailsUiAction.OnRatingChanged(it))
                                    }
                                )
                            }
                            MovieDetailsBottomSheetsType.SIGN -> {
                                GuestGiveRatingDialog(
                                    onClickCancel = { hideBottomSheet() },
                                    onClickSign = { navigateToStartScreen() }
                                )
                            }
                            MovieDetailsBottomSheetsType.DOWNLOAD -> {
                                DownloadMovieBottomSheet(
                                    onSubmitClick = {
                                        hideBottomSheet()
                                        viewModel.onAction(MovieDetailsUiAction.DownloadMovie)
                                    },
                                    onCancelClick = {
                                        hideBottomSheet()
                                        viewModel.onAction(MovieDetailsUiAction.CloseDownloadDownloadPrompt)
                                    }
                                )
                            }
                        }
                    },
                    content = {
                        MovieDetailsScreenContent(
                            movie = it,
                            castList = state.castList,
                            moviesList = state.similarMoviesList,
                            trailersList = state.trailersList,
                            isInFavourite = state.isInFavourite,
                            hasTabError = state.isTabsError,
                            isTabsLoading = state.isTabsLoading,
                            imdbUrl = state.imdbUrl,
                            showAddToFavouriteSnackBarSnackBar = state.isAddInFavouriteError,
                            showDownloadSnackBarSnackBar = state.isDownloadSnackbarShown,
                            onBackClick = onBackClick,
                            onCastClick = { },
                            onFavouriteClick = { viewModel.onAction(MovieDetailsUiAction.OnFavorite) },
                            onRateClick = {
                                visibleDialog = if (state.isGuestAccount) {
                                    MovieDetailsBottomSheetsType.SIGN
                                } else {
                                    MovieDetailsBottomSheetsType.RATING
                                }
                                showBottomSheet()
                            },
                            onMovieClick = { viewModel.onAction(MovieDetailsUiAction.OnMovie(it)) },
                            onTrailerClick = { viewModel.onAction(MovieDetailsUiAction.OnTrailer(it)) },
                            onPlayClick = { },
                            onDownloadClick = { viewModel.onAction(MovieDetailsUiAction.OpenDownloadDownloadPrompt) },
                            fetchTabSimilarMovies = { viewModel.onAction(MovieDetailsUiAction.FetchSimilarMovies) },
                            onLoadMore = { viewModel.onAction(MovieDetailsUiAction.LoadMoreSimilarMovies) },
                            isGuestAccount = state.isGuestAccount,
                        )
                    },
                    sheetBackgroundColor = MaterialTheme.colors.background,
                )
            }
        }
    }

    if (state.isDownloadDialogOpen) {
        DownloadMovieDialog(
            progress = state.videoProgressPercentage,
            size = state.videoSizeTotal,
            sizeLeft = state.videoSizeLeft,
            onHide = { viewModel.onAction(MovieDetailsUiAction.HideMovieDownloading) },
            onCancel = { viewModel.onAction(MovieDetailsUiAction.CancelMovieDownloading) }
        )
    }

    if (state.isDownloadingError) {
        MovaErrorScreen(
            isNetworkError = true,
            onDismissClick = { viewModel.onAction(MovieDetailsUiAction.HideDownloadingError) },
            onTryAgainClick = { viewModel.onAction(MovieDetailsUiAction.HideDownloadingError) },
        )
    }

    BackHandler {
        scope.launch {
            if (bottomSheetState.isVisible) {
                hideBottomSheet()
            } else {
                onBackClick()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                is MovieDetailsNavEvent.TrailerWebView -> {
                    navigateToTrailerWebView(event.key)
                }
                is MovieDetailsNavEvent.MovieDetails -> {
                    navigateToMovieDetails(event.movieId)
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MovieDetailsScreenContent(
    movie: MovieDetailsUiModel,
    castList: List<CastUiModel>,
    moviesList: List<Movie>?,
    trailersList: List<MovieTrailersResult>?,
    imdbUrl: String,
    isInFavourite: Boolean,
    hasTabError: Boolean,
    isTabsLoading: Boolean,
    showAddToFavouriteSnackBarSnackBar: Boolean,
    showDownloadSnackBarSnackBar: Boolean,
    isGuestAccount: Boolean,
    onBackClick: () -> Unit,
    onCastClick: () -> Unit,
    onFavouriteClick: () -> Unit,
    onRateClick: () -> Unit,
    onPlayClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onMovieClick: (Long) -> Unit,
    onTrailerClick: (String) -> Unit,
    onLoadMore: () -> Unit,
    fetchTabSimilarMovies: () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    var blur by remember {
        mutableStateOf(0f)
    }

    BoxWithConstraints {
        val screenHeight = maxHeight
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            item {
                Column(modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                ) {
                    MovieDetailsHeader(
                        posterImageUrl = movie.posterImageUrl,
                        onBackClick = onBackClick,
                        onCastClick = onCastClick,
                        modifier = Modifier
                            .graphicsLayer {
                                scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                                translationY = scrolledY * 0.5f
                                previousOffset = lazyListState.firstVisibleItemScrollOffset
                                blur = previousOffset * 0.004f
                            }
                            .blur(radius = blur.dp),
                    )
                    Spacer(modifier = Modifier
                        .height(14.dp)
                        .background(MaterialTheme.colors.background)
                    )
                    MovieInfoPanel(
                        title = movie.title,
                        rating = movie.averageRating,
                        year = movie.year,
                        pgRating = movie.pgRating,
                        country = movie.country,
                        imdbSharingUrl = imdbUrl,
                        isInFavourite = isInFavourite,
                        isDownloadEnabled = isGuestAccount.not(),
                        onFavouriteClick = onFavouriteClick,
                        onRateClick = onRateClick,
                        onPlayClick = onPlayClick,
                        onDownloadClick = onDownloadClick,
                        hasFavoriteButton = isGuestAccount.not(),
                    )
                    Spacer(modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                    )
                    MovieExpandableOverview(
                        genreList = movie.genresList,
                        overviewText = movie.overviewText,
                    )
                    Spacer(modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                    )
                    CastList(castList = castList)
                }
            }

            item {
                Column(modifier = Modifier.height(screenHeight)) {
                    val pagerState = rememberPagerState(initialPage = 0)
                    MovaVerticalSpacer(height = 20.dp)
                    MovieDetailsTabs(pagerState, fetchTabSimilarMovies)
                    MovieDetailsPager(
                        pagerState = pagerState,
                        moviesList = moviesList,
                        trailersList = trailersList,
                        onMovieClick = onMovieClick,
                        onTrailerClick = onTrailerClick,
                        hasError = hasTabError,
                        isLoading = isTabsLoading,
                        onLoadMore = onLoadMore,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .nestedScroll(remember {
                                object : NestedScrollConnection {
                                    override fun onPreScroll(
                                        available: Offset,
                                        source: NestedScrollSource
                                    ): Offset {
                                        return if (available.y > 0) Offset.Zero else Offset(
                                            x = 0f,
                                            y = -lazyListState.dispatchRawDelta(-available.y)
                                        )
                                    }
                                }
                            })
                    )
                }
            }
        }
        when {
            showAddToFavouriteSnackBarSnackBar ->
                MovieDetailsSnackBar(
                    textId = R.string.home_add_in_favourite_error,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            showDownloadSnackBarSnackBar ->
                MovieDetailsSnackBar(
                    textId = R.string.downloads_is_already_downloaded,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
        }
    }
}

@Composable
private fun MovieInfoPanel(
    title: String,
    rating: Float,
    year: String,
    pgRating: String?,
    country: String,
    imdbSharingUrl: String,
    isInFavourite: Boolean,
    isDownloadEnabled: Boolean,
    hasFavoriteButton: Boolean,
    onFavouriteClick: () -> Unit,
    onRateClick: () -> Unit,
    onPlayClick: () -> Unit,
    onDownloadClick: () -> Unit,
) {
    val favouriteIcon = if (isInFavourite) {
        R.drawable.ic_filled_heart
    } else {
        R.drawable.ic_outlined_heart
    }

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, imdbSharingUrl)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalScrollAnimation(
                modifier = Modifier.weight(1f),
                content = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onBackground,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            )
            if (hasFavoriteButton) {
                IconButton(onClick = onFavouriteClick) {
                    Icon(
                        painter = painterResource(id = favouriteIcon),
                        tint = Color.Unspecified,
                        contentDescription = "In favourite button"
                    )
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_share),
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Share button",
                modifier = Modifier.clickableSingle(
                    onClick = { context.startActivity(shareIntent) },
                    interactionSource = NoRippleInteractionSource()
                )
            )
        }
        Spacer(modifier = Modifier
            .height(10.dp)
            .background(MaterialTheme.colors.background)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            MovieRating(rating = rating, onRateClick = onRateClick)
            Text(
                text = year,
                style = bodyMediumSemiBold.copy(lineHeight = 19.6.sp)
            )
            if (pgRating.isNullOrEmpty().not()) BorderedText(text = pgRating!!)
            if (country.isNotEmpty()) BorderedText(text = country)
        }
        Spacer(modifier = Modifier
            .height(12.dp)
            .background(MaterialTheme.colors.background)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            PlayButton(onClick = onPlayClick, modifier = Modifier.weight(1f))
            DownloadButton(
                onClick = onDownloadClick,
                isEnabled = isDownloadEnabled,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MovieExpandableOverview(
    genreList: List<Genre>,
    overviewText: String,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val genres = stringResource(
        id = R.string.movie_details_genres,
        genreList.joinToString(", ") { it.name }
    )
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = genres,
            style = bodySmallMedium.copy(lineHeight = 14.4.sp),
            color = MaterialTheme.colors.onBackground,
            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
            overflow = TextOverflow.Ellipsis
        )
        MovaVerticalSpacer(height = 8.dp)
        Text(
            text = overviewText,
            style = bodySmallMedium.copy(lineHeight = 14.4.sp),
            color = MaterialTheme.colors.onBackground,
            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clickable {
                isExpanded = isExpanded.not()
            }
        )
    }
}

@Composable
private fun MovieDetailsSnackBar(
    @StringRes textId: Int,
    modifier: Modifier
) {
    Snackbar(modifier = modifier
        .padding(horizontal = 24.dp)
        .padding(bottom = 10.dp)) {
        Text(text = stringResource(id = textId))
    }
}

@ShowkaseComposable(
    name = "Movie Details Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMovieDetailsScreen() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        MovieDetailsScreenContent(
            movie = MovieDetailsUiModel(
                title = "Avatar: The Way of Water",
                overviewText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat... View More",
                averageRating = 5.5f,
                year = "2001",
                country = "US",
                posterImageUrl = "",
                pgRating = "1",
                genresList = listOf(
                    Genre(1, "Action"),
                    Genre(2, "Comedy"),
                    Genre(3, "TV Series"),
                ),
                voteCount = 400,
                id = 0
            ),
            castList = listOf(
                CastUiModel(
                    name = "John Doe",
                    jobTitle = "QA engineer",
                    image = ""
                ),
                CastUiModel(
                    name = "Michel Jackson",
                    jobTitle = "android Developer",
                    image = ""
                ),
                CastUiModel(
                    name = "Terminator",
                    jobTitle = "Designer",
                    image = ""
                ),
            ),
            moviesList = listOf(),
            trailersList = listOf(),
            imdbUrl = "",
            showDownloadSnackBarSnackBar = false,
            isInFavourite = false,
            hasTabError = true,
            isTabsLoading = false,
            showAddToFavouriteSnackBarSnackBar = false,
            isGuestAccount = false,
            onBackClick = { /*TODO*/ },
            onCastClick = { /*TODO*/ },
            onFavouriteClick = { /*TODO*/ },
            onRateClick = { /*TODO*/ },
            onPlayClick = { /*TODO*/ },
            onDownloadClick = { /*TODO*/ },
            onMovieClick = {},
            onTrailerClick = {},
            onLoadMore = { /*TODO*/ },
        ) {

        }
    }
}