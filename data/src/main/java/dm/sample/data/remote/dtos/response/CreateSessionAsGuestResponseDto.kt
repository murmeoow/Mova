package dm.sample.data.remote.dtos.response

import com.google.gson.annotations.SerializedName

data class CreateSessionAsGuestResponseDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("guest_session_id")
    val guestSessionId: String,
    @SerializedName("expire_at")
    val expireAt: String, // to Date
)