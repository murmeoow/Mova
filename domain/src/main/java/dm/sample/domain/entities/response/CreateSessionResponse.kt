package dm.sample.mova.domain.entities.response

data class CreateSessionResponse(
    val success: Boolean,
    val sessionId: String,
)