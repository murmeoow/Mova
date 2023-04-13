package dm.sample.data.repositories

import dm.sample.data.local.db.daos.MovieChangeDao
import dm.sample.data.local.db.daos.MovieDao
import dm.sample.data.local.db.entites.toDomain
import dm.sample.data.local.db.entites.toEntity
import dm.sample.data.local.prefsstore.MovaDataStore
import dm.sample.data.remote.ServerApi
import dm.sample.data.remote.dtos.request.RateMovieRequestDto
import dm.sample.data.remote.dtos.response.toDomain
import dm.sample.data.repositories.base.BaseRepository
import dm.sample.mova.domain.base.ServerStatusCode
import dm.sample.mova.domain.entities.Filter
import dm.sample.mova.domain.entities.FilterType
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.MovieChange
import dm.sample.mova.domain.entities.response.GetMovieDetailsResponse
import dm.sample.mova.domain.entities.response.GetMovieListResponse
import dm.sample.mova.domain.repositories.MovieRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
    private val dataStore: MovaDataStore,
    private val movieDao: MovieDao,
    private val movieChangeDao: MovieChangeDao,
    gson: Gson,
) : MovieRepository, BaseRepository(gson) {

    override suspend fun fetchPopularMovies(page: Int): GetMovieListResponse = doRequest {
        serverApi.getPopularMovies(page = page).toDomain()
    }


    override suspend fun fetchTopRatedMovies(page: Int): GetMovieListResponse = doRequest {
        serverApi.getTopRatedMovies(page = page).toDomain()
    }


    override suspend fun fetchNowPlayingMovies(page: Int): GetMovieListResponse = doRequest {
        serverApi.getNowPlayingMovies(page = page).toDomain()
    }


    override suspend fun fetchUpcomingMovies(page: Int): GetMovieListResponse = doRequest {
        serverApi.getUpcomingMovies(page).toDomain()
    }


    override suspend fun searchMovie(
        page: Int,
        keyword: String,
    ): GetMovieListResponse = doRequest {
        serverApi.searchMovie(page = page, includeAdult = false, keyword = keyword).toDomain()
    }

    override suspend fun searchMovieByFilters(page: Int, filters: List<Filter>) = doRequest {
        val genres =
            filters.filter { it.filterType == FilterType.GENRE }.joinToString(",") { it.value }
        val year = filters.firstOrNull { it.filterType == FilterType.TIME }
        val sortBy = filters.firstOrNull { it.filterType == FilterType.SORT }
        val params = mutableMapOf("page" to page.toString())
        if (genres.isNotBlank()) params["with_genres"] = genres
        year?.let {
            if (!year.isDefaultSelected) params["year"] = year.value
        }
        sortBy?.let {
            if (!sortBy.isDefaultSelected) params["sort_by"] = sortBy.value
        }
        serverApi.searchMovieWithFilters(params).toDomain()
    }

    override suspend fun getMovieDetails(movieId: Int): GetMovieDetailsResponse = doRequest {
        val sessionId = dataStore.getSessionId.first()
        serverApi.getMovieDetails(movieId, sessionId, "release_dates,videos,credits,account_states").toDomain()
    }

    override suspend fun getSimilarMovies(movieId: Int, page: Int): GetMovieListResponse = doRequest {
        serverApi.getSimilarMovies(movieId, page).toDomain()
    }

    override suspend fun rateMovie(value: Int, movieId: Int): Boolean = doRequest {
        val sessionId = dataStore.getSessionId.first()
        val response = serverApi.rateMovie(
            movieId = movieId,
            request = RateMovieRequestDto(value.toFloat()),
            sessionId = sessionId
        )
        response.statusCode == ServerStatusCode.SUCCESS
    }

    override suspend fun deleteMovieRating(movieId: Int): Boolean = doRequest {
        val sessionId = dataStore.getSessionId.first()
        val response = serverApi.deleteMovieRating(
            movieId = movieId,
            sessionId = sessionId
        )
        response.statusCode == ServerStatusCode.ITEM_DELETED_SUCCESSFULLY
    }

    override suspend fun movieChanges(): List<MovieChange> = doRequest{
        val response = serverApi.changes(page = 1)
        response.changes.filter{ it.adult != true }.map { it.toDomain() }
    }

    override suspend fun localMovieChanges(at: LocalDate?): List<MovieChange> {
        val dateAt = at ?: LocalDate.now()
        val changes = movieChangeDao.getAll().filter { it.createdAt.dayOfYear == dateAt.dayOfYear }
        return changes.map { it.toDomain() }

    }

    override suspend fun saveLocalMovieChanges(changes: List<MovieChange>): Boolean = doRequest{
        changes.map { it.toEntity() }.let { movieChangeDao.insertAll(it) }
        true
    }

    override suspend fun saveLocalMovie(movie: Movie): Boolean = doRequest {
        movieDao.insertAll(movie.toEntity())
        true
    }

    override suspend fun getLocalMovie(movieId: Int): Movie? = doRequest {
        movieDao.loadById(movieId)?.toDomain()
    }

}

