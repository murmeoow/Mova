package dm.sample.mova.domain.repositories

import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface SettingsRepository {

    fun isDarkTheme() : Flow<Boolean>

    suspend fun setDarkTheme(isDark: Boolean)

    suspend fun getCurrentLanguage() : Locale

    suspend fun updateCurrentLanguage(language: Locale)
}