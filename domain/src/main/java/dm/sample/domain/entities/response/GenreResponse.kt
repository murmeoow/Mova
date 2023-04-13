package dm.sample.mova.domain.entities.response

import dm.sample.mova.domain.entities.Genre

data class GenreResponse(
    val genres: List<Genre>
)