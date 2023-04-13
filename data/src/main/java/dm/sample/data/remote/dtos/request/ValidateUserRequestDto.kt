package dm.sample.data.remote.dtos.request

import com.google.gson.annotations.SerializedName

data class ValidateUserRequestDto(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("request_token")
    val requestToken: String,
)
