package dm.sample.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dm.sample.data.local.db.entites.DownloadedMovieEntity

@Dao
interface DownloadedMoviesDao {

    @Query("SELECT * FROM downloaded_movie_posters")
    suspend fun getAll() : List<DownloadedMovieEntity>

    @Insert
    fun insertVideo(movie: DownloadedMovieEntity)

    @Query("DELETE FROM downloaded_movie_posters WHERE download_id = :downloadId")
    fun deleteVideo(downloadId: Long)

    @Query("UPDATE downloaded_movie_posters SET download_status = :downloadingStatus WHERE download_id = :downloadId")
    fun updateVideo(downloadId: Long, downloadingStatus: Int)

    @Query("SELECT * FROM downloaded_movie_posters WHERE movie_id = :movieId")
    fun getVideoByMovieId(movieId: Long): DownloadedMovieEntity?

    @Query("DELETE FROM downloaded_movie_posters")
    fun deleteAllMovies()

}