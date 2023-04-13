package dm.sample.mova.domain.base

enum class HttpStatusCode(val code: Int) {
    HTTP_SUCCESS(200),
    HTTP_NOT_FOUND(404),
    HTTP_UNAUTHORIZED(401),
    HTTP_BAD_REQUEST(400),
    HTTP_UNKNOWN(-1);

    companion object{
        fun find(code: Int) = values().find { it.code == code } ?: HTTP_UNKNOWN
    }
}