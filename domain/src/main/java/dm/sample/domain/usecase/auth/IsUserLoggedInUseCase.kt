package dm.sample.mova.domain.usecase.auth

import dm.sample.mova.domain.repositories.AuthRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}