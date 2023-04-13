package dm.sample.data.remote.dtos.response

import com.google.gson.annotations.SerializedName

data class CreateRequestTokenResponseDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("request_token")
    val requestToken: String,
    @SerializedName("expire_at")
    val expireAt: String, // to Date
)