package dm.sample.mova.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.R
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.base.ServerStatusCode
import dm.sample.mova.domain.enums.LoginType
import dm.sample.mova.domain.usecase.auth.LoginAsGuestUseCase
import dm.sample.mova.domain.usecase.auth.LoginUserUseCase
import dm.sample.mova.domain.usecase.auth.ValidateEmailUseCase
import dm.sample.mova.domain.usecase.auth.ValidatePasswordUseCase
import dm.sample.mova.navigation.ARGUMENT_LOGIN_TYPE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val loginUserUseCase: LoginUserUseCase,
    private val loginAsGuestUseCase: LoginAsGuestUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
) : ViewModel() {

    private var loginType = LoginType.fromOrdinal(savedStateHandle[ARGUMENT_LOGIN_TYPE_ID] ?: 0)

    private val _uiState = MutableStateFlow(LoginState(isLoginAsGuest = isLoginAsGuest()))
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
        changeLoginAvailability()
    }

    fun onPasswordChanged(newPassword: String) {
        if (newPassword.length <= MAX_PASSWORD_LENGTH) {
            _uiState.update { it.copy(password = newPassword) }
            changeLoginAvailability()
        }
    }

    private fun changeLoginAvailability() {
        val emailResult = validateEmailUseCase.invoke(_uiState.value.email)
        val passwordResult = validatePasswordUseCase.invoke(_uiState.value.password)
        _uiState.update {
            if (emailResult && passwordResult) {
                it.copy(isLoginEnabled = true)
            } else {
                it.copy(isLoginEnabled = false)
            }
        }
    }

    fun isOpenDialog(status: Boolean) {
        _uiState.update { it.copy(isOpenContinueWithSocialDialog = status) }
    }

    fun loginUser() = viewModelScope.launch {
        loginUserUseCase(uiState.value.email, uiState.value.password).collectLatest { resource ->
            when (resource) {
                is Resource.Error -> {
                    _uiState.update {
                        when (resource.exception.statusCode) {
                            ServerStatusCode.INVALID_USERNAME_OR_PASSWORD -> {
                                it.copy(
                                    error = R.string.login_screen_invalid_username_password,
                                    isLoading = false
                                )
                            }
                            ServerStatusCode.ACCOUNT_DISABLED -> {
                                it.copy(
                                    error = R.string.login_screen_account_disabled,
                                    isLoading = false
                                )
                            }
                            ServerStatusCode.EMAIL_NOT_VERIFIED -> {
                                it.copy(
                                    error = R.string.login_screen_email_not_verified,
                                    isLoading = false
                                )
                            }
                            ServerStatusCode.AUTHENTICATION_FAILED -> {
                                it.copy(
                                    error = R.string.login_screen_auth_failed,
                                    isLoading = false
                                )
                            }
                            else -> {
                                it.copy(
                                    error = R.string.login_screen_error,
                                    isLoading = false
                                )
                            }
                        }
                    }
                    delay(3000)
                    resetScreenState()
                }
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, loginSucceed = true) }
                }
            }
        }
    }

    fun loginAsGuest() = viewModelScope.launch {
        loginAsGuestUseCase().collectLatest { resource ->
            when (resource) {
                is Resource.Error -> {
                    _uiState.update {
                        when (resource.exception.statusCode) {
                            ServerStatusCode.INVALID_USERNAME_OR_PASSWORD -> {
                                it.copy(
                                    error = R.string.login_screen_invalid_username_password,
                                    isLoading = false
                                )
                            }
                            ServerStatusCode.ACCOUNT_DISABLED -> {
                                it.copy(
                                    error = R.string.login_screen_account_disabled,
                                    isLoading = false
                                )
                            }
                            ServerStatusCode.EMAIL_NOT_VERIFIED -> {
                                it.copy(
                                    error = R.string.login_screen_email_not_verified,
                                    isLoading = false
                                )
                            }
                            ServerStatusCode.AUTHENTICATION_FAILED -> {
                                it.copy(
                                    error = R.string.login_screen_auth_failed,
                                    isLoading = false
                                )
                            }
                            else -> {
                                it.copy(
                                    error = R.string.login_screen_error,
                                    isLoading = false
                                )
                            }
                        }
                    }
                    delay(3000)
                    resetScreenState()
                }
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, loginSucceed = true) }
                }
            }
        }
    }

    private fun resetScreenState() {
        _uiState.update {
            it.copy(
                password = "",
                error = null,
                isLoginEnabled = false
            )
        }
    }

    private fun isLoginAsGuest() = loginType == LoginType.GUEST_LOGIN

    data class LoginState(
        val email: String = "",
        val password: String = "",
        val error: Int? = null,
        val isLoading: Boolean = false,
        val loginSucceed: Boolean = false,
        val isLoginEnabled: Boolean = false,
        val isOpenContinueWithSocialDialog: Boolean = false,
        val isLoginAsGuest: Boolean,
    )

    companion object {
        private const val MAX_PASSWORD_LENGTH = 32
    }
}