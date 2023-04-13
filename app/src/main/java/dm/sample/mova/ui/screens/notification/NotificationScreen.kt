package dm.sample.mova.ui.screens.notification

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.MovieChange
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaErrorScreen
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.notification.NotificationMovieItem
import dm.sample.mova.ui.components.profile.GuestScreen
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.viewmodel.notification.NotificationViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
    navigateOnBack: () -> Unit,
    navigateToMovieDetails: (movieId: Long) -> Unit,
    navigateToStartScreen: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            MovaLoadingScreen()
        }
        uiState.isGuestAccount -> {
            GuestScreen(
                onClick = navigateToStartScreen,
                hasBackButton = true,
                onBackClick = navigateOnBack
            )
        }
        uiState.isError -> {
            MovaErrorScreen(
                isNetworkError = uiState.isNetworkError,
                onDismissClick = { viewModel.fetchChangeList() },
                onTryAgainClick = { viewModel.fetchChangeList() }
            )
        }
        else -> {
            NotificationContent(
                navigateOnBack = navigateOnBack,
                changes = uiState.movieChanges,
                onClickMovie = navigateToMovieDetails
            )
        }
    }
}

@Composable
private fun NotificationContent(
    navigateOnBack: () -> Unit,
    changes: List<MovieChange>,
    onClickMovie: (Long) -> Unit,
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        NotificationScreenHeader(
            navigateOnBack = navigateOnBack,
            onClickOption = { /* no-op */ }
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(changes.sortedBy { it.id }) { change ->
                NotificationMovieItem(
                    movie = change.movie,
                    modifier = Modifier.padding(vertical = 4.dp),
                    isLoading = change.movie == null,
                    onClick = { onClickMovie(change.id) }
                )
            }
        }
    }
}

@Composable
private fun NotificationScreenHeader(
    navigateOnBack: () -> Unit,
    onClickOption: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(top = 10.dp, end = 10.dp)
    ) {
        HeaderWithButtonAndTitle(
            title = R.string.notification_screen_title,
            onBackClick = navigateOnBack,
            modifier = Modifier.weight(1f),
        )
        IconButton(
            onClick = onClickOption,
            interactionSource = remember { NoRippleInteractionSource() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_options),
                contentDescription = "Notification header option"
            )
        }

    }
}

@ShowkaseComposable(
    name = "Notification Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewNotificationScreen() {
    val changes = buildList {
        repeat(10) {
            add(
                MovieChange(
                    id = 1,
                    adult = true,
                    movie = null,
                ),
            )
        }
    }
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        NotificationContent(
            navigateOnBack = {},
            changes = changes,
            onClickMovie = {

            }
        )
    }
}
