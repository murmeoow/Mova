package dm.sample.mova.ui.screens.actionmenu

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.navigation.Screen
import dm.sample.mova.ui.components.BackButton
import dm.sample.mova.ui.components.MovaErrorScreen
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.actionview.VerticalGridMoviesList
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.viewmodel.actionmenu.ActionMenuNavEvent
import dm.sample.mova.ui.viewmodel.actionmenu.ActionMenuUiEvent
import dm.sample.mova.ui.viewmodel.actionmenu.ActionMenuViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import java.time.LocalDate

@Composable
fun ActionMenuScreen(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    navigateToMovieDetails: (String) -> Unit
) {
    val viewModel = hiltViewModel<ActionMenuViewModel>()

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ActionMenuNavEvent.MovieDetails -> {
                    navigateToMovieDetails(Screen.MovieDetails.passArgument(event.movieId))
                }
            }
        }
    }
    ActionScreenContent(
        titleText = state.title,
        moviesList = state.movieList,
        onBackClick = onBackClick,
        onSearchClick = onSearchClick,
        onMovieClick = { viewModel.onAction(ActionMenuUiEvent.OnMovieClick(it)) },
        onLoadMore = { viewModel.onAction(ActionMenuUiEvent.LoadMore) }
    )
    if (state.isLoading) MovaLoadingScreen()
    if (state.isError) MovaErrorScreen(
        isNetworkError = state.isNetworkError,
        onDismissClick = onBackClick,
        onTryAgainClick = { viewModel.onAction(ActionMenuUiEvent.OnTryAgainClick) },
    )
}

@Composable
private fun ActionScreenContent(
    titleText: String,
    moviesList: List<Movie>,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMovieClick: (Long) -> Unit,
    onLoadMore: () -> Unit
) {
    val lazyGridState = rememberLazyGridState()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
    ) {
        ActionMenuHeader(
            title = titleText,
            onBackClick = onBackClick,
            onSearchClick = onSearchClick,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalGridMoviesList(
            movies = moviesList,
            state = lazyGridState,
            contentPadding = PaddingValues(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
                bottom = 20.dp
            ),
            onMovieClick = { onMovieClick(it) },
            onLoadMore = onLoadMore,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clipToBounds()
        )
    }

}

@Composable
private fun ActionMenuHeader(
    title: String,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .wrapContentHeight()
            .statusBarsPadding()
            .padding(top = 10.dp)
    ) {
        BackButton(
            onClick = onBackClick, 
            modifier = Modifier
                .padding(start = 24.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onSearchClick,
            interactionSource = remember { NoRippleInteractionSource() },
            modifier = Modifier.padding(end = 24.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@ShowkaseComposable(
    name = "Action Menu Header",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HeaderPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        ActionScreenContent(
            titleText = "Most popular",
            moviesList = listOf(
                Movie(
                    adult = false,
                    backdropPath = "",
                    genreIds = emptyList(),
                    id = 1,
                    originalLanguage = "",
                    originalTitle = "",
                    overview = "",
                    popularity = 1f,
                    posterPath = "",
                    releaseDate = LocalDate.now(),
                    title = "",
                    video = false,
                    voteOverage = 7.1f,
                    voteCount = 1400f,
                ),Movie(
                    adult = false,
                    backdropPath = "",
                    genreIds = emptyList(),
                    id = 1,
                    originalLanguage = "",
                    originalTitle = "",
                    overview = "",
                    popularity = 1f,
                    posterPath = "",
                    releaseDate = LocalDate.now(),
                    title = "",
                    video = false,
                    voteOverage = 7.1f,
                    voteCount = 1400f,
                ),Movie(
                    adult = false,
                    backdropPath = "",
                    genreIds = emptyList(),
                    id = 1,
                    originalLanguage = "",
                    originalTitle = "",
                    overview = "",
                    popularity = 1f,
                    posterPath = "",
                    releaseDate = LocalDate.now(),
                    title = "",
                    video = false,
                    voteOverage = 7.1f,
                    voteCount = 1400f,
                )
            ), {}, {}, {}, {}
        )
    }
}