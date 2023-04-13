package dm.sample.mova.ui.screens.mylist

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Genre
import dm.sample.mova.navigation.Screen
import dm.sample.mova.ui.components.HeaderWithLogoTitleSearch
import dm.sample.mova.ui.components.MovaEmptyListScreen
import dm.sample.mova.ui.components.MovaErrorScreen
import dm.sample.mova.ui.components.mylist.MyListFilters
import dm.sample.mova.ui.components.mylist.MyListMovies
import dm.sample.mova.ui.components.mylist.MyListMoviesShimmer
import dm.sample.mova.ui.components.mylist.MyListSearchTextField
import dm.sample.mova.ui.components.profile.GuestScreen
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Grayscale800
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyXLargeMedium
import dm.sample.mova.ui.viewmodel.mylist.MyListUiEvent
import dm.sample.mova.ui.viewmodel.mylist.MyListUiState
import dm.sample.mova.ui.viewmodel.mylist.MyListViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyListScreen(
    onChangeBottomBarVisibility: (isVisible: Boolean) -> Unit,
    backToHome: () -> Unit,
    navigateToMovieDetails: (String) -> Unit,
    navigateToStartScreen: () -> Unit,
    onShowSnackBar: (Boolean, String) -> Unit,
) {
    val viewModel = hiltViewModel<MyListViewModel>()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.navEvent.collectLatest { event ->
            when (event) {
                is MyListViewModel.NavEvent.MovieDetails -> {
                    navigateToMovieDetails(Screen.MovieDetails.passArgument(event.movieId))
                }
                MyListViewModel.NavEvent.StartScreen -> {
                    navigateToStartScreen()
                }
            }
        }
    }

    LaunchedEffect(key1 = state.isAddInFavouriteError) {
        onShowSnackBar(
            state.isAddInFavouriteError,
            context.resources.getString(R.string.home_add_in_favourite_error)
        )
    }

    when {
        state.isError -> MovaErrorScreen(
            isNetworkError = state.isNetworkError,
            onDismissClick = backToHome,
            onTryAgainClick = { viewModel.onAction(MyListUiEvent.TryAgain) }
        )
        state.isGuestAccount -> {
            GuestScreen(
                onClick = viewModel::logout
            )
        }
        else -> MyListScreenContent(
            state = state,
            onMovieClick = { viewModel.onAction(MyListUiEvent.OnMovie(it)) },
            onFavouriteClick = { viewModel.onAction(MyListUiEvent.OnFavorite(it)) },
            onSearchValueChanged = { viewModel.onAction(MyListUiEvent.OnSearchValueChanged(it)) },
            onSearchClick = { viewModel.onAction(MyListUiEvent.OnSearchClick(true)) },
            onCloseSearchClick = { viewModel.onAction(MyListUiEvent.OnSearchClick(false)) },
            onFilterClick = { viewModel.onAction(MyListUiEvent.OnFilterClick(it)) },
            bottomBarVisibility = onChangeBottomBarVisibility
        )
    }
}

@Composable
private fun MyListScreenContent(
    state: MyListUiState,
    onMovieClick: (Long) -> Unit,
    onFavouriteClick: (Long) -> Unit,
    onSearchValueChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCloseSearchClick: () -> Unit,
    onFilterClick: (Long) -> Unit,
    bottomBarVisibility: (isVisible: Boolean) -> Unit,
) {
    if (state.isEmptyList) {
        MovaEmptyListScreen(titleId = R.string.my_list_title)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            if (state.isSearchOpen) {
                MyListSearchTextField(
                    value = state.searchValue,
                    onValueChanged = onSearchValueChanged,
                    onCloseClick = onCloseSearchClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 24.dp)
                        .padding(top = 40.dp)
                )
            } else {
                HeaderWithLogoTitleSearch(
                    title = R.string.my_list_title,
                    onSearchClick = onSearchClick,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                )
            }

            MyListFilters(
                filtersList = state.filtersList,
                selectedFilters = state.selectedFilters,
                onFilterClick = onFilterClick,
                modifier = Modifier.padding(top = 6.dp)
            )
            if (state.isLoading) {
                MyListMoviesShimmer(
                    onScrollToUp = bottomBarVisibility,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            } else if (state.moviesList.isNotEmpty()) {
                MyListMovies(
                    moviesList = state.moviesList,
                    moviesInFavourite = state.moviesInFavourite,
                    onMovieClick = onMovieClick,
                    onFavouriteClick = onFavouriteClick,
                    onScrollToUp = bottomBarVisibility,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            } else {
                MovieNotFound()
            }
        }
    }
}

@Composable
private fun MovieNotFound() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.not_found_image),
            contentDescription = "Not found 404 wallpaper",
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.not_found),
            style = MaterialTheme.typography.h4,
            color = Primary500,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.not_found_text),
            style = bodyXLargeMedium,
            color = Grayscale800,
            textAlign = TextAlign.Center
        )
    }
}

@ShowkaseComposable(
    name = "My List Screen Loading",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyListLoading() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        MyListScreenContent(
            state = MyListUiState(
                isLoading = true,
                isEmptyList = false,
                filtersList = listOf(
                    Genre(1, "All Categories"),
                    Genre(2, "Movie"),
                    Genre(3, "TV Series"),
                    Genre(4, "K-Drama"),
                ),
                selectedFilters = listOf(1)
            ),
            onMovieClick = {},
            onFavouriteClick = {},
            onSearchValueChanged = {},
            onSearchClick = { /*TODO*/ },
            onCloseSearchClick = { /*TODO*/ },
            onFilterClick = {},
            bottomBarVisibility = {})
    }
}


@ShowkaseComposable(
    name = "My List Screen Empty",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyListEmpty() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        MyListScreenContent(
            state = MyListUiState(
                isLoading = true,
                isEmptyList = true,
            ),
            onMovieClick = {},
            onFavouriteClick = {},
            onSearchValueChanged = {},
            onSearchClick = { /*TODO*/ },
            onCloseSearchClick = { /*TODO*/ },
            onFilterClick = {},
            bottomBarVisibility = {})
    }
}

@ShowkaseComposable(
    name = "My List With Text Field",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyListWithTextField() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        MyListScreenContent(
            state = MyListUiState(
                isLoading = true,
                isEmptyList = false,
                searchValue = "Search text example",
                isSearchOpen = true,
                filtersList = listOf(
                    Genre(1, "All Categories"),
                    Genre(2, "Movie"),
                    Genre(3, "TV Series"),
                    Genre(4, "K-Drama"),
                ),
                selectedFilters = listOf(1)
            ),
            onMovieClick = {},
            onFavouriteClick = {},
            onSearchValueChanged = {},
            onSearchClick = { /*TODO*/ },
            onCloseSearchClick = { /*TODO*/ },
            onFilterClick = {},
            bottomBarVisibility = {})
    }
}
