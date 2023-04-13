package dm.sample.mova.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.onboarding.CompleteOnBoardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val completeOnBoardingUseCase: CompleteOnBoardingUseCase,
) : ViewModel() {

    private val _isCompleteOnBoarding = MutableStateFlow(false)
    val isCompleteOnBoarding = _isCompleteOnBoarding.asStateFlow()

    fun onGetStarted() = viewModelScope.launch {
        completeOnBoardingUseCase()
        _isCompleteOnBoarding.value = true
    }

}