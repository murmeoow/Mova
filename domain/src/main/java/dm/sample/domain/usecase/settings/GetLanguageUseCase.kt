package dm.sample.mova.domain.usecase.settings

import dm.sample.mova.domain.repositories.SettingsRepository
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke() = settingsRepository.getCurrentLanguage()
}