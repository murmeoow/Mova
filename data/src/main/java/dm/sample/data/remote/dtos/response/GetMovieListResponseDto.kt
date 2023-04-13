package dm.sample.data.remote.dtos.response

import dm.sample.data.remote.dtos.MovieDto
import dm.sample.data.remote.dtos.toDomain
import dm.sample.mova.domain.entities.response.GetMovieListResponse
import com.google.gson.annotations.SerializedName

data class GetMovieListResponseDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPage: Int,
    @SerializedName("total_results")
    val totalResults: Int,
)


fun GetMovieListResponseDto.toDomain() = GetMovieListResponse(
    page = page,
    results = results.map { it.toDomain() },
    totalPage = totalPage,
    totalResults = totalResults,
)