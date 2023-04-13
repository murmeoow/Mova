package dm.sample.mova.domain.onboarding

import dm.sample.mova.domain.repositories.AuthRepository
import javax.inject.Inject

class IsOnBoardingCompleteUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() : Boolean = authRepository.isOnBoardingComplete()

}