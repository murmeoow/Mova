package dm.sample.mova.domain.entities

data class MovieCategory(
    val id: Int,
    val name: String,
    val movies: List<Movie>,
    val page: Int,
)