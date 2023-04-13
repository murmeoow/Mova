package dm.sample.mova.domain.usecase.actionmenu

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.MovieCategory
import dm.sample.mova.domain.entities.MovieCategoryType
import dm.sample.mova.domain.entities.response.ActionMenuResponse
import dm.sample.mova.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieListByCategoryUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
) {

    suspend operator fun invoke(id: Int, page: Int = 1) : Flow<Resource<ActionMenuResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = fetchMovieListByCategory(id, page)
            emit(Resource.Success(ActionMenuResponse(response)))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }

    private suspend fun fetchMovieListByCategory(id: Int, page: Int): MovieCategory {
        return when (id) {
            MovieCategoryType.NowPlaying.id -> moviesRepository.fetchNowPlayingMovies(page)
                .toCategory(MovieCategoryType.NowPlaying.id, MovieCategoryType.NowPlaying.categoryName)
            MovieCategoryType.TopRated.id -> moviesRepository.fetchTopRatedMovies(page)
                .toCategory(MovieCategoryType.TopRated.id, MovieCategoryType.TopRated.categoryName)
            MovieCategoryType.Popular.id -> moviesRepository.fetchPopularMovies(page)
                .toCategory(MovieCategoryType.Popular.id, MovieCategoryType.Popular.categoryName)
            MovieCategoryType.Upcoming.id -> moviesRepository.fetchUpcomingMovies(page)
                .toCategory(MovieCategoryType.Upcoming.id, MovieCategoryType.Upcoming.categoryName)
            else -> {
                MovieCategory(MovieCategoryType.Unknown.id, MovieCategoryType.Unknown.categoryName, emptyList(), 0)
            }
        }
    }
}