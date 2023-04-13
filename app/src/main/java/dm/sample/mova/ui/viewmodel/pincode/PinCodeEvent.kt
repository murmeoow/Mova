package dm.sample.mova.ui.viewmodel.pincode

sealed class PinCodeEvent {
    data class KeyBoardClick(val keyboardItem: KeyboardItem) : PinCodeEvent()
    data class ValueChanged(val field: PinCodeField, val number: String) : PinCodeEvent()
    object DismissNetworkDialog: PinCodeEvent()
    object OnSkipClick: PinCodeEvent()
    object OnBackClick: PinCodeEvent()
}