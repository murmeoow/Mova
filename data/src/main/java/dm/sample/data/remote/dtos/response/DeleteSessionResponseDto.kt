package dm.sample.data.remote.dtos.response

import dm.sample.mova.domain.entities.response.DeleteSessionResponse
import com.google.gson.annotations.SerializedName

data class DeleteSessionResponseDto(
    @SerializedName("success")
    val success: Boolean,
)
fun DeleteSessionResponseDto.toDomain() = DeleteSessionResponse(success)