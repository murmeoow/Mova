package dm.sample.mova.domain.entities.response

import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.MovieCategory

data class GetMovieListResponse(
    val page: Int,
    val results: List<Movie>,
    val totalPage: Int,
    val totalResults: Int,
) {
    fun toCategory(id: Int, categoryName: String) = MovieCategory(id, categoryName, results, page)
}

