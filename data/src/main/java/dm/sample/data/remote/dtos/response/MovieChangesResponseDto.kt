package dm.sample.data.remote.dtos.response

import dm.sample.mova.domain.entities.MovieChange
import com.google.gson.annotations.SerializedName

data class MovieChangesResponseDto(
    @SerializedName("results")
    val changes: List<MovieChangeDto>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_result")
    val totalResultCount: Int,
)


data class MovieChangeDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("adult")
    val adult: Boolean?
)

fun MovieChangeDto.toDomain() = MovieChange(id = id, adult = adult, movie = null)