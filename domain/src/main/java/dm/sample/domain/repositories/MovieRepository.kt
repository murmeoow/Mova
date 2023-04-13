package dm.sample.mova.domain.repositories

import dm.sample.mova.domain.entities.Filter
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.MovieChange
import dm.sample.mova.domain.entities.response.GetMovieDetailsResponse
import dm.sample.mova.domain.entities.response.GetMovieListResponse
import java.time.LocalDate

interface MovieRepository {
    suspend fun fetchPopularMovies(page: Int = 1): GetMovieListResponse
    suspend fun fetchTopRatedMovies(page: Int = 1): GetMovieListResponse
    suspend fun fetchNowPlayingMovies(page: Int = 1): GetMovieListResponse
    suspend fun fetchUpcomingMovies(page: Int = 1): GetMovieListResponse
    suspend fun searchMovie(page: Int, keyword: String): GetMovieListResponse

    suspend fun searchMovieByFilters(page: Int, filters: List<Filter>): GetMovieListResponse

    suspend fun getMovieDetails(movieId: Int): GetMovieDetailsResponse

    suspend fun getSimilarMovies(movieId: Int, page: Int): GetMovieListResponse

    suspend fun rateMovie(value: Int, movieId: Int): Boolean

    suspend fun deleteMovieRating(movieId: Int): Boolean

    suspend fun movieChanges(): List<MovieChange>

    suspend fun localMovieChanges(at: LocalDate?) : List<MovieChange>

    suspend fun saveLocalMovieChanges(changes: List<MovieChange>) : Boolean

    suspend fun saveLocalMovie(movie: Movie) : Boolean

    suspend fun getLocalMovie(movieId: Int) : Movie?
}