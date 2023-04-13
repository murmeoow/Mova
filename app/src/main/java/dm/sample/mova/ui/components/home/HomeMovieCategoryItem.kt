package dm.sample.mova.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dm.sample.mova.R
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyXLargeBold
import dm.sample.mova.ui.viewmodel.home.HomeMovieCategoryUI

@Composable
fun HomeMovieCategoryItem(
    category: HomeMovieCategoryUI,
    onMovieSelect: (Movie) -> Unit,
    onTryAgainClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        when {
            category.isLoading -> {
                HomeHorizontalMovieListShimmer()
            }
            category.isError -> {
                TextButton(
                    onClick = onTryAgainClick,
                    interactionSource = remember { NoRippleInteractionSource() }
                ) {
                    Text(
                        text = stringResource(R.string.try_again_text),
                        style = bodyXLargeBold,
                        color = Primary500,
                    )
                }
            }
            else -> {
                HomeHorizontalMovieList(
                    movies = category.movies,
                    onMovieSelect = onMovieSelect,
                )
            }
        }
    }
}