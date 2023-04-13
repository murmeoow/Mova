package dm.sample.mova.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecommendedMovieHeader(
    modifier: Modifier = Modifier,
    movie: Movie?,
    isInFavourite: Boolean,
    onPlayMovie: (Movie) -> Unit,
    onAddMyList: (Movie) -> Unit,
    hasAddMyListButton: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(400.dp)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = NoRippleInteractionSource(),
                onClick = onClick
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie?.posterImageUrl()),
            contentDescription = "Recommended movie poster",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            movie?.let {
                RecommendedMovieDetails(
                    movie = movie,
                    isInFavourite = isInFavourite,
                    onPlayMovie = onPlayMovie,
                    onAddMyList = onAddMyList,
                    hasAddMyListButton = hasAddMyListButton,
                )
            }
        }

    }
}


