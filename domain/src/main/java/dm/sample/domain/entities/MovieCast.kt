package dm.sample.mova.domain.entities

import dm.sample.mova.domain.Constants

data class MovieCast(
    val cast: List<Cast>,
    val crew: List<Crew>,
)

data class Crew(
    val id: Int,
    val job: String,
    val name: String,
    val imagePath: String?
) {
    fun posterImageUrl(): String {
        return if (imagePath != null) {
            Constants.MOVIE_POSTER_IMAGE_URL + imagePath
        } else {
            ""
        }
    }
}

data class Cast(
    val id: Int,
    val department: String,
    val name: String,
    val order: Int,
    val imagePath: String?
) {
    fun posterImageUrl(): String {
        return if (imagePath != null) {
            Constants.MOVIE_POSTER_IMAGE_URL + imagePath
        } else {
            ""
        }
    }
}