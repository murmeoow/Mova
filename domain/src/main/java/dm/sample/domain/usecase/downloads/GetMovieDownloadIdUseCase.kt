package dm.sample.mova.domain.usecase.downloads

import dm.sample.mova.domain.repositories.DownloadRepository
import javax.inject.Inject

class GetMovieDownloadIdUseCase @Inject constructor(
    private val downloadRepository: DownloadRepository
) {

    suspend operator fun invoke(movieId: Long) = downloadRepository.getMovieDownloadId(movieId)
}