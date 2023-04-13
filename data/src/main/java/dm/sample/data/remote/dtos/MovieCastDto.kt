package dm.sample.data.remote.dtos

import dm.sample.mova.domain.entities.Cast
import dm.sample.mova.domain.entities.Crew
import dm.sample.mova.domain.entities.MovieCast
import com.google.gson.annotations.SerializedName

data class MovieCastDto(
    @SerializedName("cast")
    val cast: List<CastDto>,
    @SerializedName("crew")
    val crew: List<CrewDto>,
)

fun MovieCastDto.toDomain() = MovieCast(
    cast = cast.map { it.toDomain() },
    crew = crew.map { it.toDomain() },
)

data class CrewDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("job")
    val job: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_path")
    val imagePath: String?
)

fun CrewDto.toDomain() = Crew(id, job, name, imagePath)

data class CastDto(
    @SerializedName("known_for_department")
    val department: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("profile_path")
    val imagePath: String?
)

fun CastDto.toDomain() = Cast(id, department, name, order, imagePath)