package dm.sample.mova.domain.usecase.downloads

import dm.sample.mova.domain.entities.response.MovieDownloadResponse
import dm.sample.mova.domain.repositories.DownloadRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DownloadVideoUseCase @Inject constructor(
    private val downloadRepository: DownloadRepository
) {
    suspend operator fun invoke(
        movieId: Long,
        movieTitle: String?,
        posterPath: String?,
    ): Flow<MovieDownloadResponse>? {
        return downloadRepository.downloadVideo(
            movieId = movieId,
            movieTitle = movieTitle?.replace(":", ""),
            posterPath = posterPath)
    }
}