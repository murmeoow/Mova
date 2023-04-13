package dm.sample.data.remote.dtos.response

import dm.sample.data.remote.dtos.CertificationsDto
import dm.sample.data.remote.dtos.GenreDto
import dm.sample.data.remote.dtos.MovieAccountStatesDto
import dm.sample.data.remote.dtos.MovieCastDto
import dm.sample.data.remote.dtos.MovieTrailersDto
import dm.sample.data.remote.dtos.toDomain
import dm.sample.mova.domain.entities.MovieDetails
import dm.sample.mova.domain.entities.response.GetMovieDetailsResponse
import dm.sample.mova.domain.entities.response.ProductionCompany
import dm.sample.mova.domain.entities.response.ProductionCountry
import dm.sample.mova.domain.entities.response.SpokenLanguage
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class GetMovieDetailsResponseDto(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryDto>,
    @SerializedName("release_date")
    val releaseDate: LocalDate?,
    @SerializedName("revenue")
    val revenue: Long,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto>,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Float,
    @SerializedName("release_dates")
    val certifications: CertificationsDto,
    @SerializedName("videos")
    val trailers: MovieTrailersDto,
    @SerializedName("credits")
    val movieCast: MovieCastDto,
    @SerializedName("account_states")
    val accountStates: MovieAccountStatesDto?
)

fun GetMovieDetailsResponseDto.toDomain() = GetMovieDetailsResponse(
    movieDetails = MovieDetails(
        adult = adult,
        backdropPath = backdropPath,
        genres = genres.map { it.toDomain() },
        id = id,
        imdbId = imdbId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity.toFloat(),
        posterPath = posterPath,
        productionCompanies = productionCompanies.map { it.toDomain() },
        productionCountries = productionCountries.map { it.toDomain() },
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteCount = voteCount,
        voteOverage = voteAverage.toFloat(),
        userRating = accountStates?.rating?.toInt(),
    ),
    certifications = certifications.toDomain(),
    trailers = trailers.toDomain(),
    movieCast = movieCast.toDomain()
)

data class ProductionCompanyDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)

fun ProductionCompanyDto.toDomain() = ProductionCompany(
    id, logoPath, name, originCountry
)

data class ProductionCountryDto(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String
)

fun ProductionCountryDto.toDomain() = ProductionCountry(iso31661, name)

data class SpokenLanguageDto(
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("name")
    val name: String
)

fun SpokenLanguageDto.toDomain() = SpokenLanguage(iso6391, name)