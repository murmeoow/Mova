package dm.sample.data.remote.dtos.response

import dm.sample.mova.domain.entities.response.CreatedList
import dm.sample.mova.domain.entities.response.GetCreatedListsResponse
import com.google.gson.annotations.SerializedName

data class GetCreatedListsResponseDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<CreatedListDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
)

data class CreatedListDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("favourite_count")
    val favouriteCount: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("list_type")
    val listType: String,
    @SerializedName("item_count")
    val itemCount: Int,
    @SerializedName("name")
    val name: String,
)

fun CreatedListDto.toDomain() = CreatedList(
    description = description,
    favouriteCount = favouriteCount,
    id = id,
    listType = listType,
    itemCount = itemCount,
    name = name
)

fun GetCreatedListsResponseDto.toDomain() = GetCreatedListsResponse(
    createdLists = results.map { it.toDomain() }
)