package dm.sample.mova.ui.viewmodel.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Account
import dm.sample.mova.domain.usecase.account.GetAccountUseCase
import dm.sample.mova.domain.usecase.account.IsLoginAsGuestUseCase
import dm.sample.mova.domain.usecase.account.UpdateAccountUseCase
import dm.sample.mova.domain.usecase.account.UpdateAvatarUseCase
import dm.sample.mova.domain.usecase.auth.LogoutUserUseCase
import dm.sample.mova.domain.usecase.countries.GetCountriesListUseCase
import dm.sample.mova.domain.usecase.settings.GetLanguageUseCase
import dm.sample.mova.domain.usecase.settings.GetSubscriptionStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getCountriesListUseCase: GetCountriesListUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAvatarUseCase: UpdateAvatarUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val isLoginAsGuest: IsLoginAsGuestUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val getSubscriptionStatusUseCase: GetSubscriptionStatusUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _navEvent = Channel<NavEvent>()
    val navEvent = _navEvent.receiveAsFlow()

    val countryList = getCountriesListUseCase()

    fun fetchData() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        if (isLoginAsGuest()) {
            _uiState.update { it.copy(isLoginAsGuest = true, isLoading = false) }
        } else {
            fetchAccountData()
        }
    }

    private suspend fun fetchAccountData() {
        getAccountUseCase().collectLatest { resource ->
            when (resource) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isError = true,
                            isLoading = false,
                        )
                    }
                }
                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            account = resource.data,
                            currentLanguage = getLanguageUseCase(),
                            hasSubscription = getSubscriptionStatusUseCase()
                        )
                    }
                }
            }
        }
    }

    fun setEditMode(isProfileEditMode: Boolean) {
        _uiState.update { it.copy(isProfileEditMode = isProfileEditMode) }
    }

    fun selectAvatar(uri: Uri?) = viewModelScope.launch {
        uri?.toString()?.let { path ->
            updateAvatarUseCase(path)
            _uiState.update {
                it.copy(account = it.account.copy(avatarPath = path))
            }
        }

    }

    fun onProfileUpdate(account: Account) = viewModelScope.launch {
        val updatedAccount = updateAccountUseCase(account = account)
        _uiState.update {
            it.copy(
                isProfileEditMode = false,
                isLoading = false,
                account = updatedAccount,
            )
        }
    }

    fun onLogout() = viewModelScope.launch{
        logoutUserUseCase().collectLatest { res ->
            when(res) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(isError = true, isLoading = false)
                    }
                }
                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(isError = false, isLoading = true)
                    }
                }
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(isError = false, isLoading = false)
                    }
                    _navEvent.send(NavEvent.StartScreen)
                }
            }
        }
    }

    data class UiState(
        val isProfileEditMode: Boolean = false,
        val account: Account = Account.empty(),
        val currentLanguage: Locale = Locale.getDefault(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val isLoginAsGuest: Boolean = false,
        val hasSubscription: Boolean = false,
    )


    sealed class NavEvent {
        object StartScreen : NavEvent()
    }
}