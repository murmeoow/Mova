package dm.sample.data.remote.dtos.response

import dm.sample.mova.domain.base.ServerStatusCode
import com.google.gson.annotations.SerializedName

data class NetworkErrorResponseDto(
    @SerializedName("status_code")
    val statusCode: ServerStatusCode? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null,
)