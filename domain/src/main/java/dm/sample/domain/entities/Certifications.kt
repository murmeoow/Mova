package dm.sample.mova.domain.entities


data class Certifications(
    val results: List<CertificationsResult>?
)

data class CertificationsResult(
    val country: String,
    val releaseDates: List<ReleaseDate>
)

data class ReleaseDate(
    val certification: String,
)
