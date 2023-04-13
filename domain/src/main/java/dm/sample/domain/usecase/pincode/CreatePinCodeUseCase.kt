package dm.sample.mova.domain.usecase.pincode

import dm.sample.mova.domain.repositories.AuthRepository
import javax.inject.Inject

class CreatePinCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(pinCode: String) =
        authRepository.savePinCode(pinCode)
}