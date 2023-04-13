package dm.sample.mova.ui.viewmodel.otp

import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem
import dm.sample.mova.ui.viewmodel.pincode.PinCodeField

sealed class OtpUiEvent {
    data class KeyBoardClick(val keyboardItem: KeyboardItem) : OtpUiEvent()
    data class ValueChanged(val field: PinCodeField, val number: String) : OtpUiEvent()
    object ResendCode : OtpUiEvent()
    object VerifyOtp : OtpUiEvent()
}