package dm.sample.data.remote.dtos.response

import dm.sample.mova.domain.entities.response.CreateMyListResponse
import com.google.gson.annotations.SerializedName

data class CreateMyListResponseDto(
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("list_id")
    val listId: Int,
)

fun CreateMyListResponseDto.toDomain() = CreateMyListResponse(listId = listId)