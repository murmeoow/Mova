package dm.sample.mova.ui.viewmodel.downloads

import dm.sample.mova.domain.entities.DownloadedMovie

sealed class DownloadsUiActions {
    data class PickVideoToDelete(val video: DownloadedMovie) : DownloadsUiActions()
    object DeleteDownloadedVideo : DownloadsUiActions()
    object TryAgain : DownloadsUiActions()
}