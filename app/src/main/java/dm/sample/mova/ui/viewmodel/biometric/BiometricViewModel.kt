package dm.sample.mova.ui.viewmodel.biometric

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.usecase.auth.UpdateBiometricStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BiometricViewModel @Inject constructor(
    private val updateBiometricStatusUseCase: UpdateBiometricStatusUseCase
) : ViewModel() {

    private val _navigateToHomeEvent = Channel<Boolean>()
    val navigateToHomeEvent = _navigateToHomeEvent.receiveAsFlow()

    fun setBiometricStatus() = viewModelScope.launch {
        updateBiometricStatusUseCase.invoke(true)
        delay(2000)
        _navigateToHomeEvent.send(true)
    }
}