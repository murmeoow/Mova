package dm.sample.data.remote.dtos.response

import dm.sample.data.remote.dtos.GenreDto
import dm.sample.data.remote.dtos.toDomain
import dm.sample.mova.domain.entities.response.GenreResponse
import com.google.gson.annotations.SerializedName

data class GenreResponseDto(
    @SerializedName("genres")
    val genres: List<GenreDto>
)

fun GenreResponseDto.toDomain() = GenreResponse(genres.map { it.toDomain() })