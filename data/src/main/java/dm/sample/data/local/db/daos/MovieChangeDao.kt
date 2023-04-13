package dm.sample.data.local.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dm.sample.data.local.db.entites.MovieChangeEntity

@Dao
interface MovieChangeDao {

    @Query("SELECT * FROM movie_changes")
    suspend fun getAll() : List<MovieChangeEntity>

    @Query("SELECT * FROM movie_changes WHERE id IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): List<MovieChangeEntity>


    @Query("SELECT * FROM movie_changes WHERE id == :movieId")
    fun loadById(movieId: Int) : MovieChangeEntity

    @Insert
    fun insertAll(movies: List<MovieChangeEntity>)

    @Delete
    fun delete(movie: MovieChangeEntity)

}