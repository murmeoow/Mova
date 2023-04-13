package dm.sample.mova.domain.usecase.moviedetails

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.toMovie
import dm.sample.mova.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading())
        try {
            val localMovie = movieRepository.getLocalMovie(movieId)
            if (localMovie == null) {
                val movie = movieRepository.getMovieDetails(movieId).movieDetails.toMovie()
                movieRepository.saveLocalMovie(movie)
                emit(Resource.Success(movie))
            } else {
                emit(Resource.Success(localMovie))
            }
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }
}