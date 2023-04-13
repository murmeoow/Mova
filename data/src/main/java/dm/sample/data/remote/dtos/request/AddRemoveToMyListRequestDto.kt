package dm.sample.data.remote.dtos.request

import dm.sample.mova.domain.entities.request.AddRemoveToMyListRequest
import com.google.gson.annotations.SerializedName

data class AddRemoveToMyListRequestDto(
    @SerializedName("media_id")
    val mediaId: Int,
)

fun AddRemoveToMyListRequest.toData() = AddRemoveToMyListRequestDto(mediaId = mediaId)