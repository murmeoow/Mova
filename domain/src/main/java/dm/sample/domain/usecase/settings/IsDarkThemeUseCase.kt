package dm.sample.mova.domain.usecase.settings

import dm.sample.mova.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsDarkThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {

    operator fun invoke() : Flow<Boolean> = settingsRepository.isDarkTheme()

}