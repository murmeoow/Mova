package dm.sample.data.remote.dtos

import dm.sample.mova.domain.entities.Certifications
import dm.sample.mova.domain.entities.CertificationsResult
import dm.sample.mova.domain.entities.ReleaseDate
import com.google.gson.annotations.SerializedName

data class CertificationsDto(
    @SerializedName("results")
    val results: List<ResultDto>?
)

fun CertificationsDto.toDomain() = Certifications(results?.map { it.toDomain() })

data class ResultDto(
    @SerializedName("iso_3166_1")
    val country: String,
    @SerializedName("release_dates")
    val releaseDates: List<ReleaseDateDto>
)

fun ResultDto.toDomain() = CertificationsResult(country, releaseDates.map { it.toDomain() })

data class ReleaseDateDto(
    @SerializedName("certification")
    val certification: String,
)

fun ReleaseDateDto.toDomain() = ReleaseDate(certification)
