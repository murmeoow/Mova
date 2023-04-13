package dm.sample.mova.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.usecase.settings.IsDarkThemeUseCase
import dm.sample.mova.domain.usecase.settings.UpdateDarkThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    isDarkThemeUseCase: IsDarkThemeUseCase,
    private val updateDarkThemeUseCase: UpdateDarkThemeUseCase,
) : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme = _isDarkTheme.asStateFlow()

    init {
        viewModelScope.launch {
            isDarkThemeUseCase().collectLatest {
                _isDarkTheme.value = it
            }
        }
    }


    fun changeDarkTheme(isDark: Boolean) = viewModelScope.launch{
        updateDarkThemeUseCase.invoke(isDark)
    }

}