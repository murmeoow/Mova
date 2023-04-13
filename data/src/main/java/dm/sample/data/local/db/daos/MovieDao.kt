package dm.sample.data.local.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dm.sample.data.local.db.entites.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getAll() : List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): List<MovieEntity>


    @Query("SELECT * FROM movies WHERE id == :movieId")
    fun loadById(movieId: Int) : MovieEntity?

    @Insert
    fun insertAll(vararg movies: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)

}