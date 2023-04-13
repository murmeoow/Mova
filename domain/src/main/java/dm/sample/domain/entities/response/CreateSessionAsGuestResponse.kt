package dm.sample.mova.domain.entities.response

data class CreateSessionAsGuestResponse(
    val success: Boolean,
    val guestSessionId: String,
    val expireAt: String, // to Date
)