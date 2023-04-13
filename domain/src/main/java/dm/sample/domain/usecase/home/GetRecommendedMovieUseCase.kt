package dm.sample.mova.domain.usecase.home

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.response.GetMovieResponse
import dm.sample.mova.domain.repositories.MovieRepository
import dm.sample.mova.domain.repositories.MyListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class GetRecommendedMovieUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
    private val myListRepository: MyListRepository
) {

    operator fun invoke() : Flow<Resource<GetMovieResponse>> = flow {
        emit(Resource.Loading())
        try {
            val calendar = Calendar.getInstance()
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val movie = moviesRepository.fetchPopularMovies(dayOfMonth).results.first()
            val isInFavorite = myListRepository.checkIsMovieInFavourite(movie.id.toInt())
            val response = GetMovieResponse(movie, isInFavorite.isChecked)
            emit(Resource.Success(response))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }

}