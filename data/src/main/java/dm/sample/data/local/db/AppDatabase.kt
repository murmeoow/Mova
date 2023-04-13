package dm.sample.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dm.sample.data.local.db.converters.LocalDateConverter
import dm.sample.data.local.db.daos.DownloadedMoviesDao
import dm.sample.data.local.db.daos.MovieChangeDao
import dm.sample.data.local.db.daos.MovieDao
import dm.sample.data.local.db.entites.DownloadedMovieEntity
import dm.sample.data.local.db.entites.MovieChangeEntity
import dm.sample.data.local.db.entites.MovieEntity

@Database(
    entities = [
        MovieEntity::class,
        MovieChangeEntity::class,
        DownloadedMovieEntity::class
    ],
    version = AppDatabase.DB_VERSION,
)
@TypeConverters(
    LocalDateConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao() : MovieDao
    abstract fun movieChangesDao() : MovieChangeDao
    abstract fun downloadedMoviesPostersDao() : DownloadedMoviesDao

    companion object{
        const val DB_VERSION = 2
        const val DB_NAME = "mova_db"
    }
}