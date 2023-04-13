package dm.sample.mova.ui.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.usecase.settings.GetLanguageUseCase
import dm.sample.mova.domain.usecase.settings.UpdateLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getCurrentLanguageUseCase: GetLanguageUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
) : ViewModel() {


    private val _selectedLanguage = MutableStateFlow(Locale.getDefault())
    val selectedLanguage = _selectedLanguage.asStateFlow()

    init {
        viewModelScope.launch {
            _selectedLanguage.value = getCurrentLanguageUseCase()
        }
    }

    fun getLanguages() : List<Locale> {
        val languages = Locale.getAvailableLocales().toMutableList()
        languages.removeAll(getSuggestedLanguages())
        languages.remove(_selectedLanguage.value)
        return languages.filter { it.displayName.isNotBlank() }
    }


    fun getSuggestedLanguages() : List<Locale> {
        val selectedLanguage = _selectedLanguage.value
        val suggestedLanguages = SUGGESTED_LANGUAGES.toMutableList()
        if (!suggestedLanguages.contains(selectedLanguage)) suggestedLanguages.add(selectedLanguage)
        return suggestedLanguages.filter { it.displayName.isNotBlank() }
    }

    fun selectLanguage(language: Locale) = viewModelScope.launch{
        updateLanguageUseCase(language)
        _selectedLanguage.value = language
    }



    companion object {
        private val SUGGESTED_LANGUAGES = listOf(
            Locale("en", "US"),
            Locale("ru", "RU"),
        )
    }

}