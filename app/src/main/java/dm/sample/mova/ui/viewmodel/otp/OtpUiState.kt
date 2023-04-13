package dm.sample.mova.ui.viewmodel.otp

data class OtpUiState(
    val firstValue: String = "",
    val secondValue: String = "",
    val thirdValue: String = "",
    val fourthValue: String = "",
    val timerCount: Int = 60,

    val hasDelete: Boolean = false,
    val isError: Boolean = false,
    val isResendCode: Boolean = false,
)