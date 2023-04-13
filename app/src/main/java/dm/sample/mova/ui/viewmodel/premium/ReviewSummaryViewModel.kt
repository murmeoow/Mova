package dm.sample.mova.ui.viewmodel.premium

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.usecase.settings.UpdateSubscriptionStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewSummaryViewModel @Inject constructor(
    private val updateSubscriptionStatusUseCase: UpdateSubscriptionStatusUseCase
) : ViewModel() {

    fun updateSubscriptionStatus() = viewModelScope.launch {
        updateSubscriptionStatusUseCase.invoke(true)
    }
}