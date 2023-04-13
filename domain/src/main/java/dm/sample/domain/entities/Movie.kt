package dm.sample.mova.domain.entities

import dm.sample.mova.domain.Constants
import java.time.LocalDate

data class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Long>,
    val id: Long,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Float,
    val posterPath: String?,
    val releaseDate: LocalDate?,
    val title: String,
    val video: Boolean,
    val voteOverage: Float,
    val voteCount: Float,
) {
    fun posterImageUrl() = Constants.MOVIE_POSTER_IMAGE_URL + posterPath
}
