package dm.sample.mova.domain.entities

data class MyListDetails(
    val createdBy: String,
    val description: String,
    val favouriteCount: Int,
    val id: Long,
    val items: List<Movie>,
    val itemCount: Int,
    val name: String,
)