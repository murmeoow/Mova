package dm.sample.mova.ui.viewmodel.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.onboarding.IsOnBoardingCompleteUseCase
import dm.sample.mova.domain.usecase.auth.IsUserLoggedInUseCase
import dm.sample.mova.domain.usecase.pincode.GetPinCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val getPinCodeUseCase: GetPinCodeUseCase,
    private val isOnBoardingCompleteUseCase: IsOnBoardingCompleteUseCase
) : ViewModel() {

    private val _uiEvent = Channel<SplashNavEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun fetch() = viewModelScope.launch {
        val userLoggedInDeferred = async { isUserLoggedInUseCase.invoke() }
        val hasPinCodeDeferred = async { getPinCodeUseCase.invoke() }
        val userLoggedIn = userLoggedInDeferred.await()
        val hasPinCode = hasPinCodeDeferred.await()
        val isCompleteOnBoarding = isOnBoardingCompleteUseCase()
        delay(3000)
        when {
            !isCompleteOnBoarding -> _uiEvent.send(SplashNavEvent.OnBoarding)
            userLoggedIn && hasPinCode.isNotEmpty() -> _uiEvent.send(SplashNavEvent.ApplyPinCode)
            userLoggedIn && hasPinCode.isEmpty() -> _uiEvent.send(SplashNavEvent.Home)
            userLoggedIn.not() -> _uiEvent.send(SplashNavEvent.Start)
        }
    }
}