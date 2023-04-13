package dm.sample.data.repositories.base

import dm.sample.data.remote.dtos.response.NetworkErrorResponseDto
import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.HttpStatusCode
import dm.sample.mova.domain.base.ServerStatusCode
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository(private val gson: Gson) {

    protected suspend fun <T> doRequest(request: suspend () -> T): T = withContext(Dispatchers.IO) {
        try {
            request.invoke()
        } catch (exception: Exception) {
            exception.printStackTrace()
            throw exception.generateBaseException() ?: generateUnknownException()
        }
    }

    private fun generateUnknownException() =
        BaseException(true, ServerStatusCode.UNKNOWN_ERROR, HttpStatusCode.HTTP_UNKNOWN, null)

    private fun Exception.generateBaseException(): BaseException? {
        try {
            if (this is HttpException) {
                if (response()?.isSuccessful == true) return null
                val errorJson = response()?.errorBody()?.string() ?: return null
                val networkResponseDto = gson.fromJson(errorJson, NetworkErrorResponseDto::class.java)
                val httpCode = HttpStatusCode.find(code())
                return BaseException(
                    isNetworkError = false,
                    statusCode = networkResponseDto.statusCode ?: ServerStatusCode.UNKNOWN_ERROR,
                    httpStatusCode = httpCode,
                    statusMessage = networkResponseDto.statusMessage,
                )
            } else {
                return null
            }
        } catch (e: Exception) {
            return null
        }
    }
}