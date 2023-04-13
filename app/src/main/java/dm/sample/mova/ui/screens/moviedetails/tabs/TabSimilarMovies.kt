package dm.sample.mova.ui.screens.moviedetails.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.actionview.VerticalGridMoviesList
import dm.sample.mova.ui.theme.bodyLargeRegular
import dm.sample.mova.ui.utils.isDarkTheme

@Composable
fun TabSimilarMoviesContent(
    moviesList: List<Movie>?,
    hasError: Boolean,
    isLoading: Boolean,
    onMovieClick: (Long) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier
) {
    if (hasError) {
        TabError()
    } else if (isLoading) {
        MovaLoadingScreen(modifier.fillMaxSize())
    } else if (moviesList?.isEmpty() == true) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.background(MaterialTheme.colors.background)
        ) {
            Image(
                painter = painterResource(R.drawable.not_found_image),
                contentDescription = "Not found 404 wallpaper",
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .align(Alignment.Center)
            )
        }
    } else {
        Box(
            modifier = modifier.background(MaterialTheme.colors.background)
        ) {
            moviesList?.let {
                VerticalGridMoviesList(
                    movies = it,
                    state = rememberLazyGridState(),
                    contentPadding = PaddingValues(24.dp),
                    onMovieClick = onMovieClick,
                    onLoadMore = onLoadMore
                )
            }
        }
    }
}

@Composable
private fun TabError() {
    val icon = if (isDarkTheme()) R.drawable.not_found_image_night else R.drawable.not_found_image
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        MovaVerticalSpacer(height = 32.dp)
        Icon(
            painter = painterResource(id = icon),
            tint = Color.Unspecified,
            contentDescription = "",
        )
        MovaVerticalSpacer(height = 16.dp)
        Text(
            text = stringResource(id = R.string.check_internet_text),
            style = bodyLargeRegular.copy(textAlign = TextAlign.Center),
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        MovaVerticalSpacer(height = 32.dp)
    }
}
