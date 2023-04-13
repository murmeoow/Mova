package dm.sample.mova.domain.entities.response

import dm.sample.mova.domain.entities.MovieCategory

data class ActionMenuResponse(
    val movieListByCategory: MovieCategory
)