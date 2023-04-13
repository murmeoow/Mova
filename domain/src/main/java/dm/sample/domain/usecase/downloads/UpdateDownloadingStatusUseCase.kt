package dm.sample.mova.domain.usecase.downloads

import dm.sample.mova.domain.repositories.DownloadRepository
import javax.inject.Inject

class UpdateDownloadingStatusUseCase @Inject constructor(
    private val downloadRepository: DownloadRepository
) {
    suspend operator fun invoke(downloadId: Long, downloadingStatus: Int): Boolean {
        return downloadRepository.updateDownloadingStatus(downloadId, downloadingStatus)
    }
}