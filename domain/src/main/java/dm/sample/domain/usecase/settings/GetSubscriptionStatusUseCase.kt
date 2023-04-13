package dm.sample.mova.domain.usecase.settings

import dm.sample.mova.domain.repositories.AccountRepository
import javax.inject.Inject

class GetSubscriptionStatusUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() = accountRepository.getSubscriptionStatus()
}