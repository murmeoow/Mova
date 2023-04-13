package dm.sample.data.remote.dtos.response

import dm.sample.data.remote.dtos.MovieDto
import dm.sample.data.remote.dtos.toDomain
import dm.sample.mova.domain.entities.MyListDetails
import com.google.gson.annotations.SerializedName

data class MyListDetailsResponseDto(
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("favourite_count")
    val favouriteCount: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("items")
    val items: List<MovieDto>,
    @SerializedName("item_count")
    val itemCount: Int,
    @SerializedName("iso_639_1")
    val iso: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String,
)

fun MyListDetailsResponseDto.toDomain() = MyListDetails(
    createdBy = createdBy,
    description = description,
    favouriteCount = favouriteCount,
    id = id,
    items = items.map { it.toDomain() },
    itemCount = itemCount,
    name = name
)