package dm.sample.mova.domain.entities

import dm.sample.mova.domain.Constants

data class MovieTrailers(
    val results: List<MovieTrailersResult>?
)

data class MovieTrailersResult(
    val id: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val site: String,
    val type: String
) {
    fun youtubeThumbnailUrl() = String.format(Constants.TRAILER_THUMBNAIL_URL, key)
}