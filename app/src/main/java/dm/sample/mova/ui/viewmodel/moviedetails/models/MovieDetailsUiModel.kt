package dm.sample.mova.ui.viewmodel.moviedetails.models

import dm.sample.mova.domain.entities.Genre
import dm.sample.mova.domain.entities.response.GetMovieDetailsResponse

data class MovieDetailsUiModel(
    val id: Long,
    val title: String,
    val averageRating: Float,
    val year: String,
    val country: String,
    val posterImageUrl: String,
    val pgRating: String?,
    val genresList: List<Genre>,
    val overviewText: String,
    val voteCount: Long,
)

fun GetMovieDetailsResponse.toUi() = MovieDetailsUiModel(
    id = movieDetails.id,
    title = movieDetails.title,
    averageRating = movieDetails.voteOverage,
    year = (movieDetails.releaseDate?.year ?: "").toString(),
    country = movieDetails.productionCountries.firstOrNull()?.name ?: "",
    posterImageUrl = movieDetails.posterImageUrl(),
    pgRating = certifications.results?.find { it.country == "US" }?.releaseDates
        ?.find { it.certification.isNotEmpty() }?.certification ?: "",
    genresList = movieDetails.genres,
    overviewText = movieDetails.overview,
    voteCount = movieDetails.voteCount.toLong(),
)