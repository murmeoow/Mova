package dm.sample.mova.ui.viewmodel.pincode

sealed class PinCodeNavEvent {
    object Biometric : PinCodeNavEvent()
    object Start : PinCodeNavEvent()
    object Home : PinCodeNavEvent()
}
