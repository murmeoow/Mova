package dm.sample.data.remote.dtos

import dm.sample.mova.domain.entities.Genre
import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)

fun GenreDto.toDomain() = Genre(id, name)