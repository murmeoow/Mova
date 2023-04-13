package dm.sample.mova.ui.screens.explorer

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Filter
import dm.sample.mova.domain.entities.FilterType
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.navigation.Screen
import dm.sample.mova.ui.components.MovaErrorScreen
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.explorer.ExploreGridMovieList
import dm.sample.mova.ui.components.explorer.ExploreGridMovieListShimmer
import dm.sample.mova.ui.components.explorer.ExploreSearchHeader
import dm.sample.mova.ui.components.explorer.filter.FilterHorizontalList
import dm.sample.mova.ui.screens.explorer.filter.FilterDialog
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Grayscale800
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyXLargeMedium
import dm.sample.mova.ui.utils.isDarkTheme
import dm.sample.mova.ui.viewmodel.ExploreViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExplorerSearchScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
    onChangeBottomBarVisibility: (isVisible: Boolean) -> Unit,
    navigateToMovieDetails: (String) -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val filters by viewModel.filters.collectAsState()

    var selectedFilters by remember { mutableStateOf(listOf<Filter>()) }

    val setMultiSelectionFilter: (Filter) -> Unit = { filter ->
        val result = selectedFilters.toMutableList()
        if (result.contains(filter)) result.remove(filter)
        else result.add(filter)
        selectedFilters = result
    }
    val setSingleSelectionFilter: (Filter) -> Unit = { filter ->
        val result = selectedFilters.filter { it.filterType != filter.filterType }.toMutableList()
        result.add(filter)
        selectedFilters = result
    }

    val coroutine = rememberCoroutineScope()

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                is ExploreViewModel.NavEvent.MovieDetails -> {
                    navigateToMovieDetails(Screen.MovieDetails.passArgument(event.movieId))
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetContent = {
            // Bottom Sheet content
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)) {
                FilterDialog(
                    filters = filters,
                    selectedFilters = selectedFilters,
                    onApplyClick = {
                        viewModel.applyFiltersAndSearch(selectedFilters)
                    },
                    onResetClick = {
                        selectedFilters = viewModel.getDefaultFilters()
                        viewModel.resetFiltersAndSearch("")
                    },
                    onAddFilter = { filter ->
                        if (filter.filterType.isSingleSelection) {
                            setSingleSelectionFilter(filter)
                        } else {
                            setMultiSelectionFilter(filter)
                        }
                    },
                    onRemoveFilter = { filter ->
                        selectedFilters = selectedFilters.filter { it != filter }
                    },
                    dismissDialog = {
                        focusManager.clearFocus()
                        coroutine.launch { bottomSheetState.hide() }
                    }
                )
            }
        },
        content = {
            // The content of screen
            ExplorerSearchScreenContent(
                state = uiState,
                onChangeBottomBarVisibility = onChangeBottomBarVisibility,
                openFilterBottomSheet = {
                    coroutine.launch {
                        focusManager.clearFocus()
                        onChangeBottomBarVisibility(false)
                        bottomSheetState.show()
                    }
                },
                searchText = searchText,
                onSearchTextChanged = viewModel::onSearchTextChanged,
                onSearchTextClear = viewModel::onSearchTextClear,
                onMovieSelect = viewModel::onMovieSelect,
                onSearch = viewModel::onSearch,
                onLoadMore = viewModel::onLoadMore,
                onRemoveFilter = { filter ->
                    selectedFilters = selectedFilters.filter { it != filter }
                    viewModel.onRemoveFilter(filter)
                },
                navigateBack = navigateBack
            )
        },
        sheetBackgroundColor = MaterialTheme.colors.background,
    )

    LaunchedEffect(key1 = filters) {
        selectedFilters = viewModel.getDefaultFilters()
    }

}


@Composable
private fun ExplorerSearchScreenContent(
    state: ExploreViewModel.UiState,
    searchText: String?,
    onSearchTextChanged: (String) -> Unit,
    onSearchTextClear: () -> Unit,
    onSearch: () -> Unit,
    onLoadMore: () -> Unit,
    onMovieSelect: (Movie) -> Unit,
    onRemoveFilter: (Filter) -> Unit,
    onChangeBottomBarVisibility: (isVisible: Boolean) -> Unit,
    openFilterBottomSheet: () -> Unit,
    navigateBack: () -> Unit
) {
    var isFilterButtonEnabled by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        ExploreSearchHeader(
            searchText = searchText ?: "",
            isEnabled = isFilterButtonEnabled,
            onSearchTextChanged = onSearchTextChanged,
            onFilterClick = openFilterBottomSheet,
            onClear = onSearchTextClear,
            isClearIconVisible = searchText.isNullOrEmpty().not()
        )

        MovaVerticalSpacer(height = 12.dp)

        FilterHorizontalList(filters = state.selectedFilters, onRemove = onRemoveFilter)

        MovaVerticalSpacer(height = 12.dp)

        when {
            state.isLoading -> {
                isFilterButtonEnabled = false
                ExploreGridMovieListShimmer(
                    onScrollToUp = onChangeBottomBarVisibility,
                )
            }
            state.isMovieNotFound -> {
                isFilterButtonEnabled = true
                ExplorerMovieNotFound()

                LaunchedEffect(key1 = Unit) {
                    onChangeBottomBarVisibility(true)
                }
            }
            state.isError -> {
                isFilterButtonEnabled = true
                MovaErrorScreen(
                    isNetworkError = state.isNetworkError,
                    onDismissClick = navigateBack,
                    onTryAgainClick = onSearch
                )
            }
            state.movies.isNotEmpty() -> {
                isFilterButtonEnabled = true
                ExploreGridMovieList(
                    movies = state.movies,
                    onLoadMore = onLoadMore,
                    onScrollToUp = onChangeBottomBarVisibility,
                    onMovieClick = onMovieSelect
                )
            }
        }
    }
}

@Composable
private fun ExplorerMovieNotFound() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = if (isDarkTheme()) {
                painterResource(R.drawable.not_found_image_night)
            } else {
                painterResource(R.drawable.not_found_image)
            },
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
            color = if (isDarkTheme()) White else Grayscale800,
            textAlign = TextAlign.Center
        )
    }

}

@ShowkaseComposable(
    name = "Explore Screen Not Found",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewExploreMovieNotFound() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        var searchText by remember {
            mutableStateOf("")
        }
        Surface(
            color = MaterialTheme.colors.surface,
        ) {
            ExplorerSearchScreenContent(
                state = ExploreViewModel.UiState(
                    movies = listOf(),
                    currentPage = 1,
                    isError = false,
                    isNetworkError = false,
                    isLoading = false,
                    isMovieNotFound = true,
                    selectedFilters = listOf(),
                ),
                onChangeBottomBarVisibility = {},
                openFilterBottomSheet = {},
                searchText = searchText,
                onSearchTextChanged = { searchText = it },
                onSearchTextClear = { searchText = "" },
                onSearch = {},
                onLoadMore = {},
                onRemoveFilter = {},
                onMovieSelect = {},
                navigateBack = {}
            )
        }
    }
}


@ShowkaseComposable(
    name = "Explore Screen With Text And Filters",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewExploreWithTextAndFilters() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        var searchText by remember {
            mutableStateOf("Search text example")
        }
        Surface(
            color = MaterialTheme.colors.surface,
        ) {
            ExplorerSearchScreenContent(
                state = ExploreViewModel.UiState(
                    movies = listOf(),
                    currentPage = 1,
                    isError = false,
                    isNetworkError = false,
                    isLoading = true,
                    isMovieNotFound = false,
                    selectedFilters = listOf(
                        Filter("K-Drama", "K-Drama", false, FilterType.GENRE),
                        Filter("South Korea", "South Korea", false, FilterType.GENRE),
                        Filter("Action", "Action", false, FilterType.GENRE),
                        Filter("2022", "2022", false, FilterType.GENRE),
                    ),
                ),
                onChangeBottomBarVisibility = {},
                openFilterBottomSheet = {},
                searchText = searchText,
                onSearchTextChanged = { searchText = it },
                onSearchTextClear = { searchText = "" },
                onSearch = {},
                onLoadMore = {},
                onRemoveFilter = {},
                onMovieSelect = {},
                navigateBack = {}
            )
        }
    }
}

@ShowkaseComposable(
    name = "Explore Screen Loading",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewExploreLoading() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        var searchText by remember {
            mutableStateOf("")
        }
        Surface(
            color = MaterialTheme.colors.surface,
        ) {
            ExplorerSearchScreenContent(
                state = ExploreViewModel.UiState(
                    movies = listOf(),
                    currentPage = 1,
                    isError = false,
                    isNetworkError = false,
                    isLoading = true,
                    isMovieNotFound = false,
                    selectedFilters = listOf(),
                ),
                onChangeBottomBarVisibility = {},
                openFilterBottomSheet = {},
                searchText = searchText,
                onSearchTextChanged = { searchText = it },
                onSearchTextClear = { searchText = "" },
                onSearch = {},
                onLoadMore = {},
                onRemoveFilter = {},
                onMovieSelect = {},
                navigateBack = {}
            )
        }
    }
}

