package dm.sample.mova.domain.entities

data class DownloadedMovie(
    val id: Int?,
    val name: String,
    val posterPath: String?,
    val downloadId: Long,
    val downloadStatus: Int,
    val movieId: Long,
)