package dm.sample.mova.ui.viewmodel.pincode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.usecase.auth.IsBiometricEnabledUseCase
import dm.sample.mova.domain.usecase.auth.IsUserLoggedInUseCase
import dm.sample.mova.domain.usecase.auth.LogoutUserUseCase
import dm.sample.mova.domain.usecase.pincode.CreatePinCodeUseCase
import dm.sample.mova.domain.usecase.pincode.GetPinCodeUseCase
import dm.sample.mova.navigation.ARGUMENT_PIN_CODE_MODE
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.BACKSPACE
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.EIGHT
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.FIVE
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.FOUR
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.LOG_OUT
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.NINE
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.ONE
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.SEVEN
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.SIX
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.THREE
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.TWO
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem.ZERO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinCodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    isBiometricEnabledUseCase: IsBiometricEnabledUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val createPinCodeUseCase: CreatePinCodeUseCase,
    private val getPinCodeUseCase: GetPinCodeUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(PinCodeUiState())
    var uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<PinCodeNavEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var attempts = 3
    private var mode = PinCodeMode.fromId(savedStateHandle.get<Int?>(ARGUMENT_PIN_CODE_MODE) ?: 0)
    private val pinCode = ArrayDeque<String>(listOf())
    private var firstPinCode = ArrayDeque<String>(listOf())

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    hasLogout = if (mode == PinCodeMode.Apply) isUserLoggedInUseCase.invoke() else false,
                    pinCodeMode = mode,
                    isBiometricShown = isBiometricEnabledUseCase.invoke()
                )
            }
        }
    }

    fun onEvent(event: PinCodeEvent) = viewModelScope.launch {
        when (event) {
            PinCodeEvent.DismissNetworkDialog -> _uiState.update { it.copy(isNetworkError = false) }
            is PinCodeEvent.KeyBoardClick -> onKeyBoardClick(event.keyboardItem)
            is PinCodeEvent.ValueChanged -> pinCodeValueChanged(event.field, event.number)
            PinCodeEvent.OnSkipClick -> _uiEvent.send(PinCodeNavEvent.Home)
            PinCodeEvent.OnBackClick -> onBackClick()
        }
    }

    private fun onKeyBoardClick(keyboardItem: KeyboardItem) {
        when (keyboardItem) {
            ZERO, ONE, TWO, THREE, FOUR, FIVE,
            SIX, SEVEN, EIGHT, NINE -> enterNumber(keyboardItem.value!!)
            BACKSPACE -> deletePinCode()
            LOG_OUT -> if (_uiState.value.hasLogout) logout()
        }
    }

    fun validatePinCode() {
        when {
            pinCode.size == 4 && mode == PinCodeMode.Create -> {
                mode = PinCodeMode.Confirm
                firstPinCode.addAll(pinCode)
                viewModelScope.launch {
                    delay(1000)
                    resetScreen()
                    _uiState.update { it.copy(pinCodeMode = PinCodeMode.Confirm) }
                }
            }
            pinCode.size == 4 && mode == PinCodeMode.Confirm -> {
                if (pinCode == firstPinCode) {
                    viewModelScope.launch {
                        createPinCodeUseCase.invoke(pinCode.toString())
                        _uiEvent.send(PinCodeNavEvent.Biometric)
                    }
                } else {
                    viewModelScope.launch {
                        _uiState.update { it.copy(hasError = true) }
                        delay(2000)
                        resetScreen()
                    }
                }
            }
            pinCode.size == 4 && mode == PinCodeMode.Apply -> {
                viewModelScope.launch {
                    val savedPin = getPinCodeUseCase.invoke()
                    if (savedPin == pinCode.toString()) {
                        _uiEvent.send(PinCodeNavEvent.Home)
                    } else {
                        attempts--
                        if (attempts < 1) {
                            viewModelScope.launch {
                                _uiState.update { it.copy(isAccessDenied = true) }
                                delay(3000)
                                _uiState.update { it.copy(isAccessDenied = false) }
                                logout()
                            }
                        } else {
                            _uiState.update { it.copy(
                                hasError = true,
                                attempts = attempts
                            ) }
                            delay(2000)
                            resetScreen()
                        }
                    }
                }
            }
        }
    }

    private fun deletePinCode() {
        pinCode.removeLastOrNull()
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

    private fun logout() = viewModelScope.launch {
        logoutUserUseCase.invoke().collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isNetworkError = true, isLoading = false) }
                }
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                    delay(2000)
                }
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiEvent.send(PinCodeNavEvent.Start)
                }
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
        if (pinCode.size < 4) pinCode.add(number)
    }

    private fun resetScreen() {
        pinCode.clear()
        _uiState.update {
            it.copy(
                firstValue = "",
                secondValue = "",
                thirdValue = "",
                fourthValue = "",
                hasError = false,
                hasDelete = false
            )
        }
    }

    private fun pinCodeValueChanged(field: PinCodeField, value: String) {
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
                        fourthValue = value
                    )
                }
            }
        }
    }

    private fun onBackClick() {
        mode = PinCodeMode.Create
        _uiState.update { it.copy(pinCodeMode = PinCodeMode.Create) }
        resetScreen()
        firstPinCode.clear()
    }

    data class PinCodeUiState(
        val firstValue: String = "",
        val secondValue: String = "",
        val thirdValue: String = "",
        val fourthValue: String = "",
        val attempts: Int = 3,
        val pinCodeMode: PinCodeMode = PinCodeMode.Apply,

        val hasLogout: Boolean = false,
        val hasDelete: Boolean = false,
        val hasError: Boolean = false,
        val isLoading: Boolean = false,
        val isAccessDenied: Boolean = false,
        val isNetworkError: Boolean = false,
        val isBiometricShown: Boolean = false
    )
}