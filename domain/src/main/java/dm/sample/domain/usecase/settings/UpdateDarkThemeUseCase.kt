package dm.sample.mova.domain.usecase.settings

import dm.sample.mova.domain.repositories.SettingsRepository
import javax.inject.Inject

class UpdateDarkThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(isDark: Boolean) {
        settingsRepository.setDarkTheme(isDark)
    }

}