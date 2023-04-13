package dm.sample.mova.domain.usecase.auth

import dm.sample.mova.domain.repositories.AuthRepository
import javax.inject.Inject

class UpdateBiometricStatusUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(isEnabled: Boolean) = authRepository.updateBiometricStatus(isEnabled)
}