package dm.sample.data.remote.dtos.request

import com.google.gson.annotations.SerializedName

data class CreateSessionRequestDto(
    @SerializedName("request_token")
    val requestToken: String
)