package dm.sample.mova.domain.usecase.settings

import dm.sample.mova.domain.repositories.SettingsRepository
import java.util.*
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(language: Locale) {
        settingsRepository.updateCurrentLanguage(language)
    }

}