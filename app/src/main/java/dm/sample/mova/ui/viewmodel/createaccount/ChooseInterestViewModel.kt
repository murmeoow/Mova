package dm.sample.mova.ui.viewmodel.createaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.entities.Genre
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.usecase.genres.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseInterestViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChooseInterestUiState())
    val uiState: StateFlow<ChooseInterestUiState> = _uiState.asStateFlow()

    fun fetchGenres() = viewModelScope.launch {
        getGenresUseCase().collectLatest { resource ->
            when(resource) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isError = true, isLoading = false) }
                }
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    _uiState.update { it.copy(
                        interestList = resource.data.genres,
                        isLoading = false
                    ) }
                }
            }
        }
    }

    data class ChooseInterestUiState(
        val interestList: List<Genre> = listOf(),
        val isError: Boolean = false,
        val isLoading: Boolean = false
    )
}