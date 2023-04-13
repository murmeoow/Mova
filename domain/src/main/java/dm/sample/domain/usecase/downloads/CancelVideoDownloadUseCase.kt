package dm.sample.mova.domain.usecase.downloads

import dm.sample.mova.domain.repositories.DownloadRepository
import javax.inject.Inject

class CancelVideoDownloadUseCase @Inject constructor(
    private val downloadRepository: DownloadRepository
) {
    suspend operator fun invoke(downloadId: Long) {
        downloadRepository.cancelDownloading(downloadId)
    }
}