package dm.sample.data.remote.dtos.response

import dm.sample.mova.domain.entities.response.CreateSessionResponse
import com.google.gson.annotations.SerializedName

data class CreateSessionResponseDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("session_id")
    val sessionId: String,
)

fun CreateSessionResponseDto.toDomain() = CreateSessionResponse(success, sessionId)