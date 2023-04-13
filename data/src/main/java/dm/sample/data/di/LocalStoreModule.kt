package dm.sample.data.di

import android.content.Context
import androidx.room.Room
import dm.sample.data.local.db.AppDatabase
import dm.sample.data.local.db.daos.DownloadedMoviesDao
import dm.sample.data.local.db.daos.MovieChangeDao
import dm.sample.data.local.db.daos.MovieDao
import dm.sample.data.local.prefsstore.MovaDataStore
import dm.sample.data.local.prefsstore.MovaDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [LocalStoreProvider::class])
@InstallIn(SingletonComponent::class)
interface LocalStoreModule {

    @Binds
    fun bindMovaDataStore(dataStore: MovaDataStoreImpl): MovaDataStore

}


@Module
@InstallIn(SingletonComponent::class)
object LocalStoreProvider {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideMovieChangesDao(db: AppDatabase) : MovieChangeDao {
        return db.movieChangesDao()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(db: AppDatabase) : MovieDao {
        return db.moviesDao()
    }

    @Singleton
    @Provides
    fun provideDownloadedMoviesPostersDao(db: AppDatabase): DownloadedMoviesDao {
        return db.downloadedMoviesPostersDao()
    }

}