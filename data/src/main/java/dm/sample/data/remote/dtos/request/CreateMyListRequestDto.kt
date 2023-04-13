package dm.sample.data.remote.dtos.request

import dm.sample.mova.domain.entities.request.CreateMyListRequest
import com.google.gson.annotations.SerializedName

data class CreateMyListRequestDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
)

fun CreateMyListRequest.toData() = CreateMyListRequestDto(name, description)