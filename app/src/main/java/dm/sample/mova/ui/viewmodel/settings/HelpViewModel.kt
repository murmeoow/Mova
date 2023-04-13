package dm.sample.mova.ui.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Faq
import dm.sample.mova.domain.entities.FaqCategory
import dm.sample.mova.domain.usecase.settings.GetFaqCategoryUseCase
import dm.sample.mova.domain.usecase.settings.GetFaqsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelpViewModel @Inject constructor(
    private val getFaqCategoryUseCase: GetFaqCategoryUseCase,
    private val getFaqsUseCase: GetFaqsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    fun fetch() = viewModelScope.launch{
        fetchCategories()
        fetchFaqs()
    }

    private suspend fun fetchCategories() {
        getFaqCategoryUseCase().collectLatest { resource ->
            when (resource) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, isServiceUnavailable = true) }
                }
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true, isServiceUnavailable = false) }
                }
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            faqCategories = resource.data,
                            selectedFaqCategories = it.selectedFaqCategories.ifEmpty {
                                resource.data.take(1)
                            }.toMutableList(),
                            isLoading = false,
                            isServiceUnavailable = false
                        )
                    }
                }
            }
        }
    }

    private suspend fun fetchFaqs() {
        val selectedCategories = _uiState.value.selectedFaqCategories
        val searchText = _uiState.value.searchText
        getFaqsUseCase(categories = selectedCategories, keyword = searchText).collectLatest { resource ->
            when (resource) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, isServiceUnavailable = true) }
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(faqs = resource.data, isLoading = false)
                    }
                }
            }
        }
    }

    fun selectFaqCategory(category: FaqCategory) = viewModelScope.launch {
        _uiState.update {
            val selectedCategories = it.selectedFaqCategories.toMutableList().apply {
                if (contains(category)) remove(category)
                else add(category)
            }
            if (selectedCategories.isEmpty()) it
            else it.copy(selectedFaqCategories = selectedCategories)
        }
        fetchFaqs()
    }

    fun onContactClick() {
        _uiState.update { it.copy(isServiceUnavailable = true) }
    }

    fun dismissDialog() {
        _uiState.update { it.copy(isServiceUnavailable = false) }
    }

    fun onSearchTextChange(searchText: String) = viewModelScope.launch {
        if(searchText.length < MAX_SEARCH_TEXT_LENGTH) {
            _uiState.update { it.copy(searchText = searchText) }
            fetchFaqs()
        }
    }

    data class UiState(
        val faqCategories: List<FaqCategory> = emptyList(),
        val selectedFaqCategories: List<FaqCategory> = mutableListOf(),
        val faqs: List<Faq> = emptyList(),
        val isLoading: Boolean = false,
        val isServiceUnavailable: Boolean = false,
        val searchText: String = "",
    )

    companion object{
        const val MAX_SEARCH_TEXT_LENGTH = 64
    }

}