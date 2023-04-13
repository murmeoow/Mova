package dm.sample.mova.ui.components.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.ui.components.MovaOutlinedButton
import dm.sample.mova.ui.components.MovaRoundedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.theme.Black
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodySmallMedium
import dm.sample.mova.ui.utils.isImageDark
import dm.sample.mova.ui.utils.loadBitmapFromUrl

@Composable
fun RecommendedMovieDetails(
    movie: Movie,
    isInFavourite: Boolean,
    onPlayMovie: (Movie) -> Unit,
    onAddMyList: (Movie) -> Unit,
    hasAddMyListButton: Boolean,
) {
    val context = LocalContext.current
    var textColor by remember { mutableStateOf(Color.White) }

    LaunchedEffect(key1 = Unit) {
        val bitmap = loadBitmapFromUrl(context, movie.posterImageUrl())
        textColor = if (isImageDark(bitmap, 0, bitmap.height / 2, bitmap.width, bitmap.height)) White else Black
    }

    Column(Modifier.padding(all = 24.dp)) {
        Text(
            text = movie.title,
            color = textColor,
            style = MaterialTheme.typography.h4,
        )
        MovaVerticalSpacer(height = 8.dp)
        Text(
            text = movie.overview,
            color = textColor,
            style = bodySmallMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        MovaVerticalSpacer(height = 8.dp)

        Row { // Actions
            MovaRoundedButton(
                text = stringResource(R.string.play),
                icon = painterResource(R.drawable.ic_circle_play),
            ) {
                onPlayMovie(movie)
            }
            Spacer(Modifier.width(12.dp))
            if (hasAddMyListButton) {
                MovaOutlinedButton(
                    text = stringResource(R.string.my_list),
                    isChecked = isInFavourite,
                    checkedIcon = painterResource(R.drawable.ic_outlined_heart),
                    nonCheckedIcon = painterResource(R.drawable.ic_plus),
                    onClick = { onAddMyList(movie) }
                )
            }
        }
    }
}