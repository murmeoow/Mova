package dm.sample.mova.domain.usecase.pincode

import dm.sample.mova.domain.repositories.AuthRepository
import javax.inject.Inject

class GetPinCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() =
        authRepository.getPinCode()
}