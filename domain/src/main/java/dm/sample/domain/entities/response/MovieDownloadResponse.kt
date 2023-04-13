package dm.sample.mova.domain.entities.response

class MovieDownloadResponse(
    val downloadId: Long,
    val videoProgressPercentage: Int = 0,
    val videoSizeTotal: Float = 0f,
    val videoSizeLeft: Float = 0f,
    val isError: Boolean = false,
)