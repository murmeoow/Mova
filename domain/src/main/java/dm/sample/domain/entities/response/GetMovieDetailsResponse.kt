package dm.sample.mova.domain.entities.response

import dm.sample.mova.domain.entities.Certifications
import dm.sample.mova.domain.entities.MovieCast
import dm.sample.mova.domain.entities.MovieDetails
import dm.sample.mova.domain.entities.MovieTrailers

data class GetMovieDetailsResponse(
    val movieDetails: MovieDetails,
    val certifications: Certifications,
    val trailers: MovieTrailers,
    val movieCast: MovieCast,
    val isInFavourite: Boolean? = null
)

data class ProductionCompany(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String
)

data class ProductionCountry(
    val iso31661: String,
    val name: String
)

data class SpokenLanguage(
    val iso6391: String,
    val name: String
)