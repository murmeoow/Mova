package dm.sample.data.remote.dtos.response

import dm.sample.mova.domain.entities.response.GetAccountDetailsResponse
import com.google.gson.annotations.SerializedName

data class GetAccountDetailsResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String,
)

fun GetAccountDetailsResponseDto.toDomain() = GetAccountDetailsResponse(id, name, username)