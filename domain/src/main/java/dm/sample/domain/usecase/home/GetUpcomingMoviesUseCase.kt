package dm.sample.mova.domain.usecase.home

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.MovieCategory
import dm.sample.mova.domain.entities.MovieCategoryType
import dm.sample.mova.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetUpcomingMoviesUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
) {

    operator fun invoke(): Flow<Resource<MovieCategory>> = flow{
        emit(Resource.Loading())
        try {
            val response = moviesRepository.fetchUpcomingMovies()
            val category = response.toCategory(
                MovieCategoryType.Upcoming.id,
                MovieCategoryType.Upcoming.categoryName,
            )
            emit(Resource.Success(category))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }

}