package dm.sample.mova.domain.entities.response

data class CreateRequestTokenResponse(
    val success: Boolean,
    val requestToken: String,
    val expireAt: String, // to Date
)