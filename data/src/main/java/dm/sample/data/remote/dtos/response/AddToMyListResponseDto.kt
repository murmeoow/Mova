package dm.sample.data.remote.dtos.response

import com.google.gson.annotations.SerializedName

data class AddToMyListResponseDto(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
)
