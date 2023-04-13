package dm.sample.mova.domain.entities

enum class MovieCategoryType(val id: Int, val categoryName: String){
    NowPlaying(id = 0, categoryName = "Now Playing Movies"),
    Popular(id = 1, categoryName = "Popular Movies"),
    TopRated(id = 2, categoryName = "Top Rated Movies"),
    Upcoming(id = 3, categoryName = "Upcoming Movies"),
    Unknown(id = -1, categoryName = "Unknown category");

    companion object {
        fun fromId(id: Int) = values().first { it.id == id }
    }
}