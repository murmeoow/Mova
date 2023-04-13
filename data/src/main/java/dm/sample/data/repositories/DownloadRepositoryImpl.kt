package dm.sample.data.repositories

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import dm.sample.mova.data.R
import dm.sample.data.local.db.daos.DownloadedMoviesDao
import dm.sample.data.local.db.entites.DownloadedMovieEntity
import dm.sample.data.local.db.entites.toDomain
import dm.sample.data.local.db.entites.toEntity
import dm.sample.data.models.DownloadingStatus
import dm.sample.data.repositories.base.BaseRepository
import dm.sample.data.utils.getBytesDownloaded
import dm.sample.data.utils.getBytesTotal
import dm.sample.data.utils.getCursorById
import dm.sample.data.utils.getCursorByStatus
import dm.sample.data.utils.getDownloadStatus
import dm.sample.data.utils.isDownloadingRunning
import dm.sample.data.utils.startDownload
import dm.sample.mova.domain.entities.DownloadedMovie
import dm.sample.mova.domain.entities.response.MovieDownloadResponse
import dm.sample.mova.domain.repositories.DownloadRepository
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class DownloadRepositoryImpl @Inject constructor(
    private val downloadedMoviesDao: DownloadedMoviesDao,
    private val downloadManager: DownloadManager,
    @ApplicationContext private val context: Context,
    gson: Gson,
) : DownloadRepository, BaseRepository(gson) {

    override suspend fun fetchAllDownloadedMovies(): List<DownloadedMovie> {
        return downloadedMoviesDao.getAll().map { it.toDomain() }
    }

    override suspend fun getDownloadsMovaFolderFileNames(): List<String>? {
        val downloadsFolder =
            Environment.getExternalStoragePublicDirectory("${Environment.DIRECTORY_DOWNLOADS}/Mova/")
        return if (downloadsFolder.isDirectory) {
            downloadsFolder?.listFiles()?.map { it.name }?.toList()
        } else {
            null
        }
    }

    override suspend fun deleteDownloadedMovie(downloadId: Long, movieTitle: String) = doRequest {
        val uri = Uri.parse(
            "${
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            }/Mova/${movieTitle}.mp4"
        )
        val file = File(uri.path)
        if (file.exists()) {
            file.deleteRecursively()
        }
        downloadedMoviesDao.deleteVideo(downloadId)
    }

    override suspend fun addDownloadedMovie(video: DownloadedMovie) = doRequest {
        downloadedMoviesDao.insertVideo(video.toEntity())
    }

    @SuppressLint("Range")
    override suspend fun deleteAllDownloadedMovies() = doRequest {
        val file = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/Mova")
        file.deleteRecursively()
        downloadedMoviesDao.deleteAllMovies()
        val cursor = downloadManager.getCursorByStatus(
            DownloadManager.STATUS_RUNNING or DownloadManager.STATUS_PENDING or
                  DownloadManager.STATUS_FAILED or DownloadManager.STATUS_PAUSED
        )
        while (cursor.moveToNext()) {
            downloadManager.remove(cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID)))
        }
        cursor.close()
    }

    override suspend fun updateDownloadingStatus(
        downloadId: Long,
        downloadingStatus: Int
    ): Boolean = doRequest {
        downloadedMoviesDao.updateVideo(downloadId, downloadingStatus)
        true
    }

    override suspend fun downloadVideo(
        movieId: Long,
        movieTitle: String?,
        posterPath: String?,
    ): Flow<MovieDownloadResponse>? {

        val downloadMovie = getVideoById(movieId)
        val currentDownloadId: Long = downloadMovie?.downloadId ?: 0L
        return when {
            currentDownloadId == 0L -> startNewDownload(movieId, movieTitle, posterPath)
            isDownloadStatusFailed(currentDownloadId) -> {
                deleteDownloadedMovie(currentDownloadId, downloadMovie?.name ?: "")
                startNewDownload(movieId, movieTitle, posterPath)
            }
            isDownloadStatusSuccessful(currentDownloadId) -> flowOf(MovieDownloadResponse(currentDownloadId))
            else -> getDownloadProgress(currentDownloadId)
        }
    }

    private suspend fun getVideoById(movieId: Long) : DownloadedMovieEntity? = withContext(Dispatchers.IO) {
        return@withContext downloadedMoviesDao.getVideoByMovieId(movieId)
    }

    private suspend fun startNewDownload(
        movieId: Long,
        movieTitle: String?,
        posterPath: String?,
    ): Flow<MovieDownloadResponse>? {
        try {
            val downloadId = downloadManager.startDownload(
                downloadUrl = VIDEO_URL,
                downloadTitle = movieTitle,
                downloadDescription = context.getString(R.string.video_is_downloading),
            )
            val movie = DownloadedMovie(
                id = null,
                name = movieTitle ?: "Untitled",
                posterPath = posterPath ?: "",
                downloadId = downloadId,
                downloadStatus = DownloadingStatus.Downloading.ordinal,
                movieId = movieId
            )
            addDownloadedMovie(movie)
            return getDownloadProgress(downloadId)

        } catch (exception: Exception) {
            return null
        }
    }


    private fun isDownloadStatusFailed(downloadId: Long): Boolean {
        val status = downloadManager.getDownloadStatus(downloadId)
        return status == DownloadManager.STATUS_FAILED
    }

    private fun isDownloadStatusSuccessful(downloadId: Long): Boolean {
        val status = downloadManager.getDownloadStatus(downloadId)
        return status == DownloadManager.STATUS_SUCCESSFUL
    }

    private fun getDownloadProgress(downloadId: Long): Flow<MovieDownloadResponse> = flow {
        var downloading = true
        while (downloading) {
            var isError = false
            val cursor = downloadManager.getCursorById(downloadId)
            if (cursor.moveToFirst()) {
                val bytesDownloaded = cursor.getBytesDownloaded()
                val bytesTotal = cursor.getBytesTotal()
                when (cursor.getDownloadStatus()) {
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        downloading = false
                        downloadedMoviesDao.updateVideo(downloadId, DownloadingStatus.Success.ordinal)
                    }
                    DownloadManager.STATUS_FAILED -> {
                        downloading = false
                        isError = true
                        downloadedMoviesDao.updateVideo(downloadId, DownloadingStatus.Failed.ordinal)
                    }
                }
                emit(
                    MovieDownloadResponse(
                        downloadId = downloadId,
                        videoProgressPercentage = ((bytesDownloaded * 100L) / bytesTotal).toInt(),
                        videoSizeLeft = bytesDownloaded / (1024 * 1024),
                        videoSizeTotal = bytesTotal / (1024 * 1024),
                        isError = isError
                    )
                )
            }
            cursor.close()
        }
    }.flowOn(Dispatchers.IO)

    @SuppressLint("Range")
    override suspend fun isDownloadRunning(downloadId: Long): Boolean {
        return downloadManager.isDownloadingRunning(downloadId)
    }

    override suspend fun cancelDownloading(downloadId: Long) {
        downloadManager.remove(downloadId)
        withContext(Dispatchers.IO) {
            downloadedMoviesDao.deleteVideo(downloadId)
        }
    }

    override suspend fun getMovieDownloadId(movieId: Long): Long? = withContext(Dispatchers.IO) {
        return@withContext downloadedMoviesDao.getVideoByMovieId(movieId)?.downloadId
    }

    companion object {
        private const val VIDEO_URL =
            "https://drive.google.com/u/1/uc?id=1QMxNSzWwfTIiV49r4uCcKSo5X2QP7W4p&export=download"
    }
}