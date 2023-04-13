package dm.sample.mova.domain.base

sealed class Resource<out T> {

    class Loading<T>(val data: T? = null) : Resource<T>()

    class Success<T>(val data: T) : Resource<T>()

    class Error<T>(
        val exception: BaseException
    ) : Resource<T>()

}
