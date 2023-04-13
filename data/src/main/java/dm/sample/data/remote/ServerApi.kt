package dm.sample.data.remote

import DeleteSessionRequestDto
import dm.sample.data.remote.dtos.request.AddRemoveToMyListRequestDto
import dm.sample.data.remote.dtos.request.CreateMyListRequestDto
import dm.sample.data.remote.dtos.request.CreateSessionRequestDto
import dm.sample.data.remote.dtos.request.RateMovieRequestDto
import dm.sample.data.remote.dtos.request.ValidateUserRequestDto
import dm.sample.data.remote.dtos.response.AddToMyListResponseDto
import dm.sample.data.remote.dtos.response.CheckIsMovieInMyListResponseDto
import dm.sample.data.remote.dtos.response.CreateMyListResponseDto
import dm.sample.data.remote.dtos.response.CreateRequestTokenResponseDto
import dm.sample.data.remote.dtos.response.CreateSessionAsGuestResponseDto
import dm.sample.data.remote.dtos.response.CreateSessionResponseDto
import dm.sample.data.remote.dtos.response.DeleteSessionResponseDto
import dm.sample.data.remote.dtos.response.GenreResponseDto
import dm.sample.data.remote.dtos.response.GetAccountDetailsResponseDto
import dm.sample.data.remote.dtos.response.GetCreatedListsResponseDto
import dm.sample.data.remote.dtos.response.GetMovieDetailsResponseDto
import dm.sample.data.remote.dtos.response.GetMovieListResponseDto
import dm.sample.data.remote.dtos.response.MovaBaseResponseDto
import dm.sample.data.remote.dtos.response.MovieChangesResponseDto
import dm.sample.data.remote.dtos.response.MyListDetailsResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ServerApi {

    @GET("/3/authentication/guest_session/new")
    suspend fun createSessionAsGuest(): CreateSessionAsGuestResponseDto

    @GET("/3/authentication/token/new")
    suspend fun createRequestToken(): CreateRequestTokenResponseDto

    @POST("3/authentication/session/new")
    suspend fun createSession(@Body request: CreateSessionRequestDto): CreateSessionResponseDto

    @HTTP(method = "DELETE", path = "3/authentication/session", hasBody = true)
    suspend fun deleteSession(@Body request: DeleteSessionRequestDto) : DeleteSessionResponseDto

    @POST("/3/authentication/token/validate_with_login")
    suspend fun validateUser(@Body request: ValidateUserRequestDto): CreateRequestTokenResponseDto

    @GET("/3/genre/movie/list")
    suspend fun getGenre(): GenreResponseDto

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): GetMovieListResponseDto

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): GetMovieListResponseDto

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): GetMovieListResponseDto

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): GetMovieListResponseDto

    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean,
        @Query("query") keyword: String,
    ) : GetMovieListResponseDto

    /**
     * Also note that a number of filters support being comma (,) or pipe (|) separated.
     * Comma's are treated like an AND and query while pipe's are an OR.
     * @docs https://developers.themoviedb.org/3/discover/movie-discover
     */
    @GET("/3/discover/movie")
    suspend fun searchMovieWithFilters(
        @QueryMap params: Map<String, String>
    ) : GetMovieListResponseDto


    @POST("/3/list")
    @Headers("Content-Type: application/json")
    suspend fun createMyList(
        @Query("session_id") sessionId: String,
        @Body request: CreateMyListRequestDto
    ) : CreateMyListResponseDto

    @GET("/3/list/{list_id}")
    suspend fun getMyListDetails(
        @Path("list_id") listId: Int
    ) : MyListDetailsResponseDto

    @GET("/3/list/{list_id}/item_status")
    suspend fun checkIsMovieInFavourite(
        @Path("list_id") listId: Int,
        @Query("movie_id") movieId: Int,
    ) : CheckIsMovieInMyListResponseDto

    @POST("/3/list/{list_id}/add_item")
    @Headers("Content-Type: application/json")
    suspend fun addMovieToMyList(
        @Path("list_id") listId: Int,
        @Query("session_id") sessionId: String,
        @Body request: AddRemoveToMyListRequestDto
    ) : AddToMyListResponseDto

    @POST("/3/list/{list_id}/remove_item")
    suspend fun removeMovieFromMyList(
        @Header("Content-Type") contentType: String = "application/json",
        @Path("list_id") listId: Int,
        @Query("session_id") sessionId: String,
        @Body request: AddRemoveToMyListRequestDto
    ) : AddToMyListResponseDto

    @DELETE("/3/list/{list_id}")
    suspend fun deleteMyList(
        @Path("list_id") listId: Int,
        @Query("session_id") sessionId: String,
    ) : AddToMyListResponseDto


    @GET("/3/account/{account_id}/lists")
    suspend fun getCreatedLists(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
    ) : GetCreatedListsResponseDto

    @GET("/3/account")
    suspend fun getAccountDetails(
        @Query("session_id") sessionId: String,
    ) : GetAccountDetailsResponseDto


    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String,
        @Query("append_to_response") appendToResponse: String,
    ): GetMovieDetailsResponseDto

    @GET("/3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
    ): GetMovieListResponseDto

    @POST("/3/movie/{movie_id}/rating")
    @Headers("Content-Type: application/json;charset=utf-8")
    suspend fun rateMovie(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String,
        @Body request: RateMovieRequestDto
    ): MovaBaseResponseDto

    @DELETE("/3/movie/{movie_id}/rating")
    @Headers("Content-Type: application/json")
    suspend fun deleteMovieRating(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String,
    ): MovaBaseResponseDto

    @GET("/3/movie/changes?page=1")
    suspend fun changes(
        @Query("page") page: Int,
    ) : MovieChangesResponseDto
}