package dm.sample.mova.domain.entities

data class MovieChange(
    val id: Long,
    val adult: Boolean?,
    val movie: Movie?,
)
