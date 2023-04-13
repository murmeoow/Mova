package dm.sample.mova.ui.viewmodel.createaccount

import androidx.lifecycle.ViewModel
import dm.sample.mova.domain.usecase.auth.ValidateEmailUseCase
import dm.sample.mova.domain.usecase.auth.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateAccountScreenState())
    val uiState: StateFlow<CreateAccountScreenState> = _uiState.asStateFlow()

    private fun changeSignUpAvailability() {
        val emailResult = validateEmailUseCase.invoke(_uiState.value.email)
        val passwordResult = validatePasswordUseCase.invoke(_uiState.value.password)
        val passwordsEquals = _uiState.value.password == _uiState.value.repeatedPassword
        _uiState.update {
            if (emailResult && passwordResult && passwordsEquals) {
                it.copy(isSignUpEnabled = true)
            } else {
                it.copy(isSignUpEnabled = false)
            }
        }
    }

    fun onEmailChanged(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
        changeSignUpAvailability()
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
        changeSignUpAvailability()
    }

    fun onRepeatedPasswordChanged(newRepeatedPassword: String) {
        _uiState.update { it.copy(repeatedPassword = newRepeatedPassword) }
        changeSignUpAvailability()
    }

    fun isOpenDialog(status: Boolean) {
        _uiState.update { it.copy(openDialog = status) }
    }

    data class CreateAccountScreenState(
        val email: String = "",
        val password: String = "",
        val repeatedPassword: String = "",
        val isLoading: Boolean = false,
        val isSignUpEnabled: Boolean = false,
        val openDialog: Boolean = false
    )
}