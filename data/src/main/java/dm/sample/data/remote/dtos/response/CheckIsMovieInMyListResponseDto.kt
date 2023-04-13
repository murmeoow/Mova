package dm.sample.data.remote.dtos.response

import dm.sample.mova.domain.entities.response.CheckIsMovieInMyListResponse
import com.google.gson.annotations.SerializedName

data class CheckIsMovieInMyListResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("item_present")
    val status: Boolean,
)
fun CheckIsMovieInMyListResponseDto.toDomain() = CheckIsMovieInMyListResponse(isChecked = status)