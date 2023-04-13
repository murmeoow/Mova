package dm.sample.mova.domain.usecase.account

import dm.sample.mova.domain.enums.LoginType
import dm.sample.mova.domain.repositories.AuthRepository
import javax.inject.Inject

class IsLoginAsGuestUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke() : Boolean {
        return if (authRepository.isUserLoggedIn()) {
            authRepository.getLoginType() == LoginType.GUEST_LOGIN
        } else {
            false
        }
    }

}