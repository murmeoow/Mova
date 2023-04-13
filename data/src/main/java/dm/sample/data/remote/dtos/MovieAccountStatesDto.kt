package dm.sample.data.remote.dtos

data class MovieAccountStatesDto(
    val id: Int?,
    var rating: Float?,
    var rated: Boolean?,
    val favorite: Boolean?,
    val watchlist: Boolean?,
)