package dm.sample.data.repositories

import dm.sample.data.local.prefsstore.MovaDataStore
import dm.sample.mova.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.*
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: MovaDataStore,
) : SettingsRepository {

    override fun isDarkTheme(): Flow<Boolean> {
        return dataStore.isDarkTheme
    }

    override suspend fun setDarkTheme(isDark: Boolean) {
        dataStore.setDarkTheme(isDark)
    }

    override suspend fun getCurrentLanguage(): Locale {
        return dataStore.getLanguage.first()
    }

    override suspend fun updateCurrentLanguage(language: Locale) {
        dataStore.setLanguage(language)
    }


}