package dm.sample.data.di

import dm.sample.data.repositories.AccountRepositoryImpl
import dm.sample.data.repositories.AuthRepositoryImpl
import dm.sample.data.repositories.CountriesRepositoryImpl
import dm.sample.data.repositories.DownloadRepositoryImpl
import dm.sample.data.repositories.FaqRepositoryImpl
import dm.sample.data.repositories.FileRepositoryImpl
import dm.sample.data.repositories.FilterRepositoryImpl
import dm.sample.data.repositories.GenreRepositoryImpl
import dm.sample.data.repositories.MovieRepositoryImpl
import dm.sample.data.repositories.MyListRepositoryImpl
import dm.sample.data.repositories.SettingsRepositoryImpl
import dm.sample.mova.domain.repositories.AccountRepository
import dm.sample.mova.domain.repositories.AuthRepository
import dm.sample.mova.domain.repositories.CountriesRepository
import dm.sample.mova.domain.repositories.DownloadRepository
import dm.sample.mova.domain.repositories.FaqRepository
import dm.sample.mova.domain.repositories.FileRepository
import dm.sample.mova.domain.repositories.FiltersRepository
import dm.sample.mova.domain.repositories.GenreRepository
import dm.sample.mova.domain.repositories.MovieRepository
import dm.sample.mova.domain.repositories.MyListRepository
import dm.sample.mova.domain.repositories.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindGenreRepository(genreRepository: GenreRepositoryImpl): GenreRepository

    @Binds
    fun bindMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository

    @Binds
    fun bindFiltersRepository(filterRepository: FilterRepositoryImpl): FiltersRepository

    @Binds
    fun bindCountriesRepository(countriesRepository: CountriesRepositoryImpl): CountriesRepository


    @Binds
    fun bindAccountRepository(accountRepository: AccountRepositoryImpl): AccountRepository

    @Binds
    fun bindMyLisRepository(myListRepository: MyListRepositoryImpl): MyListRepository

    @Binds
    fun bindFileRepository(fileRepository: FileRepositoryImpl): FileRepository

    @Binds
    fun bindSettingsRepository(settingsRepository: SettingsRepositoryImpl) : SettingsRepository

    @Binds
    fun bindFaqRepository(faqRepository: FaqRepositoryImpl) : FaqRepository

    @Binds
    fun bindDownloadRepository(downloadRepositoryImpl: DownloadRepositoryImpl): DownloadRepository

}