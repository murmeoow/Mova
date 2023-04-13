package dm.sample.mova.domain.usecase.auth

import dm.sample.mova.domain.repositories.AuthRepository
import javax.inject.Inject

class IsBiometricEnabledUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() = authRepository.isBiometricEnabled()
}