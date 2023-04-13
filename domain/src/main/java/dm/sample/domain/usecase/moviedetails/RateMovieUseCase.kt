package dm.sample.mova.domain.usecase.moviedetails

import dm.sample.mova.domain.repositories.MovieRepository
import javax.inject.Inject

class RateMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(value: Int?, movieId: Int): Boolean {
        return if (value != null) {
            movieRepository.rateMovie(value, movieId)
        } else {
            movieRepository.deleteMovieRating(movieId)
        }
    }
}