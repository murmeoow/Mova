package dm.sample.data.local.db.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dm.sample.mova.domain.entities.DownloadedMovie

@Entity(tableName = "downloaded_movie_posters")
data class DownloadedMovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "download_id")
    val downloadId: Long,
    @ColumnInfo(name = "download_status")
    val downloadStatus: Int,
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
)


fun DownloadedMovie.toEntity() = DownloadedMovieEntity(
    id = id,
    posterPath = posterPath,
    name = name,
    downloadId = downloadId,
    downloadStatus = downloadStatus,
    movieId = movieId
)

fun DownloadedMovieEntity.toDomain() = DownloadedMovie(
    id = id,
    posterPath = posterPath,
    name = name,
    downloadId = downloadId,
    downloadStatus = downloadStatus,
    movieId = movieId
)