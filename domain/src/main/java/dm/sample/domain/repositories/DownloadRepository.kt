package dm.sample.mova.domain.repositories

import dm.sample.mova.domain.entities.DownloadedMovie
import dm.sample.mova.domain.entities.response.MovieDownloadResponse
import kotlinx.coroutines.flow.Flow

interface DownloadRepository {

    suspend fun fetchAllDownloadedMovies(): List<DownloadedMovie>

    suspend fun getDownloadsMovaFolderFileNames() : List<String>?

    suspend fun deleteDownloadedMovie(downloadId: Long, movieTitle: String)

    suspend fun addDownloadedMovie(video: DownloadedMovie)

    suspend fun deleteAllDownloadedMovies()

    suspend fun updateDownloadingStatus(downloadId: Long, downloadingStatus: Int): Boolean

    suspend fun downloadVideo(
        movieId: Long,
        movieTitle: String?,
        posterPath: String?,
    ): Flow<MovieDownloadResponse>?

    suspend fun isDownloadRunning(downloadId: Long) : Boolean

    suspend fun cancelDownloading(downloadId: Long)

    suspend fun getMovieDownloadId(movieId: Long): Long?
}