package dm.sample.mova.domain.repositories

import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.response.CheckIsMovieInMyListResponse

interface MyListRepository {

    suspend fun getMyListDetails(): List<Movie>?

    suspend fun checkIsMovieInFavourite(movieId: Int): CheckIsMovieInMyListResponse

    suspend fun addMovieToMyList(movieId: Int):  Boolean

    suspend fun removeMovieFromMyList(movieId: Long): Boolean
}