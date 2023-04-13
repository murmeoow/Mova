package dm.sample.data.remote.dtos

import dm.sample.mova.domain.entities.MovieTrailers
import dm.sample.mova.domain.entities.MovieTrailersResult
import com.google.gson.annotations.SerializedName

data class MovieTrailersDto(
    @SerializedName("results")
    val results: List<MovieTrailersResultDto>?
)

fun MovieTrailersDto.toDomain() = MovieTrailers(
    results = results?.map { it.toDomain() }
)

data class MovieTrailersResultDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("official")
    val official: Boolean,
    @SerializedName("site")
    val site: String,
    @SerializedName("type")
    val type: String
)

fun MovieTrailersResultDto.toDomain() = MovieTrailersResult(id, key, name, official, site, type)