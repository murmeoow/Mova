package dm.sample.mova.domain.entities

import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.response.ProductionCompany
import dm.sample.mova.domain.entities.response.ProductionCountry
import java.time.LocalDate

data class MovieDetails(
    val adult: Boolean,
    val backdropPath: String?,
    val genres: List<Genre>,
    val id: Long,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val popularity: Float,
    val posterPath: String?,
    val releaseDate: LocalDate?,
    val title: String,
    val video: Boolean,
    val voteOverage: Float,
    val voteCount: Float,
    val userRating: Int?
) {
    fun posterImageUrl() = Constants.MOVIE_POSTER_IMAGE_URL + posterPath
}

fun MovieDetails.toMovie() = Movie(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genres.map { it.id.toLong() },
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteCount = voteCount,
    voteOverage = voteOverage,
    video = video
)