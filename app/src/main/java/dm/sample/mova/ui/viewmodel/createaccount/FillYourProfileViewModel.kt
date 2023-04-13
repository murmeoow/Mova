package dm.sample.mova.ui.viewmodel.createaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.entities.Account
import dm.sample.mova.domain.entities.Country
import dm.sample.mova.domain.usecase.account.UpdateAccountUseCase
import dm.sample.mova.domain.usecase.countries.GetCountriesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FillYourProfileViewModel @Inject constructor(
    getCountriesListUseCase: GetCountriesListUseCase,
    val updateAccountUseCase: UpdateAccountUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FillYourProfileUiState())
    val uiState: StateFlow<FillYourProfileUiState> = _uiState.asStateFlow()

    val countriesList = getCountriesListUseCase.invoke()

    fun onFullNameChanged(value: String) {
        _uiState.update { it.copy(fullName = value) }
    }

    fun onNicknameChanged(value: String) {
        _uiState.update { it.copy(nickname = value) }
    }

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value) }
    }

    fun onPhoneNumberChanged(value: String) {
        _uiState.update { it.copy(phoneNumber = value) }
    }

    fun onGenderChanged(value: String) {
        _uiState.update { it.copy(gender = value) }
    }

    fun onSelectedCountryChanged(value: Country) {
        _uiState.update { it.copy(selectedCountry = value) }
    }

    fun onUpdateProfile() = viewModelScope.launch {
        _uiState.value.apply {
            updateAccountUseCase(
                Account(
                    id = -1,
                    fullname = fullName,
                    nickname = nickname,
                    email = email,
                    phone = phoneNumber,
                    phoneCode = selectedCountry.code,
                    gender = gender,
                    avatarPath = null,
                ),
            )
        }
    }

    data class FillYourProfileUiState(
        val fullName: String = "",
        val nickname: String = "",
        val email: String = "",
        val phoneNumber: String = "",
        val gender: String = "",
        val selectedCountry: Country = Country("ad", "376", "Andorra"),
    )
}