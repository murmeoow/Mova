package dm.sample.mova.domain.usecase.downloads

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.DownloadedMovie
import dm.sample.mova.domain.repositories.DownloadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllDownloadedVideoUseCase @Inject constructor(
    private val downloadRepository: DownloadRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<DownloadedMovie>>> = flow {
        try {
            val allVideos = downloadRepository.fetchAllDownloadedMovies()
            val downloadFiles = downloadRepository.getDownloadsMovaFolderFileNames() ?: listOf()

            val result = allVideos.mapNotNull { movie ->
                when {
                    downloadRepository.isDownloadRunning(movie.downloadId) -> {
                        val updatedMovie = movie.copy(downloadStatus = 2)
                        downloadRepository.updateDownloadingStatus(
                            updatedMovie.downloadId,
                            updatedMovie.downloadStatus
                        )
                        updatedMovie
                    }
                    isExistInDownloads(movie, downloadFiles) -> {
                        val updatedMovie = movie.copy(downloadStatus = 1)
                        downloadRepository.updateDownloadingStatus(
                            updatedMovie.downloadId,
                            updatedMovie.downloadStatus
                        )
                        updatedMovie
                    }
                    else -> {
                        downloadRepository.deleteDownloadedMovie(movie.downloadId, movie.name)
                        null
                    }
                }
            }


            emit(Resource.Success(result))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }

    private fun isExistInDownloads(movie: DownloadedMovie, downloadFolderFiles : List<String>): Boolean {
        val searchTargetFileName = "${movie.name}.mp4"
        return downloadFolderFiles.contains(searchTargetFileName)

    }
}