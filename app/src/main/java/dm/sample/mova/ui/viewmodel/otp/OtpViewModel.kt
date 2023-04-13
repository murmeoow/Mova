package dm.sample.mova.ui.viewmodel.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.ui.utils.CountDownTimerUtil
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem
import dm.sample.mova.ui.viewmodel.pincode.PinCodeField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.ArrayDeque

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val countDownTimer: CountDownTimerUtil
): ViewModel() {

    private var _uiState = MutableStateFlow(OtpUiState())
    var uiState = _uiState.asStateFlow()

    private val _navEvent = Channel<Boolean>()
    val navEvent = _navEvent.receiveAsFlow()

    private val otpCode = ArrayDeque<String>(listOf())

    init {
        setupTimer()
        countDownTimer.startTimer(TIMER_DURATION)
    }

    fun onEvent(event: OtpUiEvent) = viewModelScope.launch {
        when (event) {
            is OtpUiEvent.KeyBoardClick -> onKeyBoardClick(event.keyboardItem)
            is OtpUiEvent.ValueChanged -> otpCodeValueChanged(event.field, event.number)
            OtpUiEvent.ResendCode -> {
                setupTimer()
                countDownTimer.startTimer(TIMER_DURATION)
            }
            OtpUiEvent.VerifyOtp -> verifyOtp()
        }
    }

    private fun onKeyBoardClick(keyboardItem: KeyboardItem) {
        when (keyboardItem) {
            KeyboardItem.ZERO, KeyboardItem.ONE,
            KeyboardItem.TWO, KeyboardItem.THREE,
            KeyboardItem.FOUR, KeyboardItem.FIVE,
            KeyboardItem.SIX, KeyboardItem.SEVEN,
            KeyboardItem.EIGHT, KeyboardItem.NINE -> enterNumber(keyboardItem.value!!)
            KeyboardItem.BACKSPACE -> deletePinCode()
            else -> {}
        }
    }

    private fun verifyOtp() = viewModelScope.launch {
        if (otpCode.joinToString("") == MOCKED_OTP) {
            _navEvent.send(true)
        } else {
            _uiState.update { it.copy(isError = true) }
            delay(2000)
            _uiState.update { it.copy(isError = false) }
            resetScreen()
        }
    }

    private fun deletePinCode() {
        otpCode.removeLastOrNull()
        when {
            _uiState.value.fourthValue.isNotBlank() -> {
                _uiState.update { it.copy(fourthValue = "") }
            }
            _uiState.value.thirdValue.isNotBlank() -> {
                _uiState.update { it.copy(thirdValue = "") }
            }
            _uiState.value.secondValue.isNotBlank() -> {
                _uiState.update { it.copy(secondValue = "") }
            }
            _uiState.value.firstValue.isNotBlank() -> {
                _uiState.update { it.copy(firstValue = "", hasDelete = false) }
            }
        }
    }

    private fun enterNumber(number: String) {
        when {
            _uiState.value.firstValue.isBlank() -> {
                _uiState.update { it.copy(firstValue = number, hasDelete = true,) }
            }
            _uiState.value.secondValue.isBlank() -> {
                _uiState.update { it.copy(secondValue = number) }
            }
            _uiState.value.thirdValue.isBlank() -> {
                _uiState.update { it.copy(thirdValue = number) }
            }
            _uiState.value.fourthValue.isBlank() -> {
                _uiState.update { it.copy(fourthValue = number) }
            }
        }
        if (otpCode.size < 4) otpCode.add(number)
    }

    private fun otpCodeValueChanged(field: PinCodeField, value: String) {
        when (field) {
            PinCodeField.FIRST -> {
                if (_uiState.value.firstValue.isEmpty()) {
                    _uiState.update { it.copy(
                        firstValue = value
                    ) }
                }
            }
            PinCodeField.SECOND -> {
                if (_uiState.value.secondValue.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        secondValue = value
                    )
                }
            }
            PinCodeField.THIRD -> {
                if (_uiState.value.thirdValue.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        thirdValue = value
                    )
                }
            }
            PinCodeField.FOURTH -> {
                if (_uiState.value.fourthValue.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        fourthValue = value,
                    )
                }
            }
        }
    }

    private fun resetScreen() {
        otpCode.clear()
        _uiState.update {
            it.copy(
                firstValue = "",
                secondValue = "",
                thirdValue = "",
                fourthValue = "",
                isError = false,
                hasDelete = false
            )
        }
    }

    private fun setupTimer() {
        countDownTimer.onTick = { timerCount ->
            if (timerCount > 0L) {
                _uiState.update { it.copy(
                    timerCount = timerCount.toInt(),
                    isResendCode = false
                ) }
            }
        }
        countDownTimer.onFinish = {
            _uiState.update { it.copy(
                isResendCode = true
            ) }
        }
    }

    companion object {
        private const val MOCKED_OTP = "1111"
        private const val TIMER_DURATION = 60000L
    }
}