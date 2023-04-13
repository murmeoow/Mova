package dm.sample.mova.domain.usecase.moviedetails

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.response.GetMovieListResponse
import dm.sample.mova.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSimilarMoviesListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int = 1): Flow<Resource<GetMovieListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = movieRepository.getSimilarMovies(movieId, page)
            emit(Resource.Success(response))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }
}