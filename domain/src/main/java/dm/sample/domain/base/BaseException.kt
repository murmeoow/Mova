package dm.sample.mova.domain.base

class BaseException(
    val isNetworkError: Boolean,
    val statusCode: ServerStatusCode,
    val httpStatusCode: HttpStatusCode,
    val statusMessage: String? = null,
) : Exception() {
    companion object{
        val UNKNOWN_BASE_EXCEPTION = BaseException(
            isNetworkError = false,
            statusCode = ServerStatusCode.UNKNOWN_ERROR,
            httpStatusCode = HttpStatusCode.HTTP_UNKNOWN,
            statusMessage = null,
        )
    }
}