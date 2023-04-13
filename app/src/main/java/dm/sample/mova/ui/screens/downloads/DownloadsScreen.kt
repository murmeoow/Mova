package dm.sample.mova.ui.screens.downloads

import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.data.models.DownloadingStatus
import dm.sample.mova.domain.entities.DownloadedMovie
import dm.sample.mova.ui.components.HeaderWithLogoTitleSearch
import dm.sample.mova.ui.components.MovaEmptyListScreen
import dm.sample.mova.ui.components.MovaErrorScreen
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.downloads.DownloadedMovie
import dm.sample.mova.ui.components.profile.GuestScreen
import dm.sample.mova.ui.utils.isScrollingUp
import dm.sample.mova.ui.viewmodel.downloads.DownloadsUiActions
import dm.sample.mova.ui.viewmodel.downloads.DownloadsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DownloadsScreen(
    viewModel: DownloadsViewModel = hiltViewModel(),
    navigateToStartScreen: () -> Unit,
    onChangeBottomBarVisibility: (Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val toggleBottomSheet = {
        scope.launch {
            if (bottomSheetState.isVisible) {
                onChangeBottomBarVisibility(true)
                bottomSheetState.hide()
            } else {
                onChangeBottomBarVisibility(false)
                bottomSheetState.show()
            }
        }
    }

    val playVideoFromFile: (String) -> Unit = {
        val uri = Uri.parse(
            "${
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            }/Mova/$it.mp4"
        )
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setDataAndType(uri, "video/mp4")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(context, intent, null)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.navEvent.collectLatest { event ->
            when (event) {
                DownloadsViewModel.NavEvent.StartScreen -> {
                    navigateToStartScreen()
                }
            }
        }
    }

    LaunchedEffect(key1 = bottomSheetState) {
        snapshotFlow { bottomSheetState.isVisible }.collect { isVisible ->
            if (isVisible) {
                onChangeBottomBarVisibility(false)
            } else {
                onChangeBottomBarVisibility(true)
            }
        }
    }

    when {
        uiState.isLoading -> {
            MovaLoadingScreen()
        }
        uiState.isGuestAccount -> {
            GuestScreen(onClick = viewModel::logout)
        }
        uiState.isError -> {
            MovaErrorScreen(
                isNetworkError = false,
                onDismissClick = { viewModel.onAction(DownloadsUiActions.TryAgain) },
                onTryAgainClick = { viewModel.onAction(DownloadsUiActions.TryAgain) },
            )
        }
        uiState.videosList.isEmpty() -> {
            MovaEmptyListScreen(
                textId = R.string.downloads_empty_list,
                titleId = R.string.nav_downloads,
                isDownloadScreen = true
            )
        }
        else -> {
            ModalBottomSheetLayout(
                sheetState = bottomSheetState,
                sheetContent = {
                    DownloadBottomSheet(
                        downloadedMovie = uiState.videoToDelete,
                        onCancelClick = { toggleBottomSheet() },
                        onSubmitClick = {
                            viewModel.onAction(DownloadsUiActions.DeleteDownloadedVideo)
                            toggleBottomSheet()
                        }
                    )
                },
                content = {
                    DownloadsScreenContent(
                        videosList = uiState.videosList,
                        onDeleteClick = {
                            viewModel.onAction(DownloadsUiActions.PickVideoToDelete(it))
                            toggleBottomSheet()
                        },
                        onVideoClick = { playVideoFromFile(it) },
                        bottomBarVisibility = onChangeBottomBarVisibility
                    )
                },
                sheetBackgroundColor = MaterialTheme.colors.background,
                sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            )
        }
    }
}

@Composable
private fun DownloadsScreenContent(
    videosList: List<DownloadedMovie>,
    onVideoClick: (String) -> Unit,
    onDeleteClick: (DownloadedMovie) -> Unit,
    bottomBarVisibility: (isVisible: Boolean) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        HeaderWithLogoTitleSearch(
            title = R.string.nav_downloads,
            modifier = Modifier.padding(horizontal = 24.dp),
            onSearchClick = {}
        )
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(horizontal = 24.dp)
        ) {
            item {
                MovaVerticalSpacer(height = 30.dp)
            }
            items(videosList.size) { index ->
                DownloadedMovie(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    downloadedMovie = videosList[index],
                    isDownloaded = videosList[index].downloadStatus == DownloadingStatus.Downloading.ordinal,
                    onDeleteClick = onDeleteClick,
                    onVideoClick = onVideoClick
                )
            }
            item {
                MovaVerticalSpacer(height = 40.dp)
            }
        }
    }

    val isScrollingUp = lazyListState.isScrollingUp()
    LaunchedEffect(isScrollingUp) {
        bottomBarVisibility(isScrollingUp)
    }
}

@Preview
@Composable
fun DownloadsScreenContentPreview() {
    DownloadsScreenContent(
        listOf(
            DownloadedMovie(1, "Video Name", "", 0, 0,0),
            DownloadedMovie(1, "Video Name", "", 0, 0,0),
            DownloadedMovie(1, "Video Name", "", 0, 0,0),
            DownloadedMovie(1, "Video Name", "", 0, 0,0),
            DownloadedMovie(1, "Video Name", "", 0, 0,0),
        ),
        {}, {}, {}
    )
}