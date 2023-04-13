package dm.sample.mova.domain.entities.response

import dm.sample.mova.domain.entities.Movie

data class GetMovieResponse(
    val movie: Movie,
    val isInFavourite: Boolean,
)

