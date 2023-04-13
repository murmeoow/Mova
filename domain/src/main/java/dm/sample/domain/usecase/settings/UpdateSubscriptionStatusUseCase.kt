package dm.sample.mova.domain.usecase.settings

import dm.sample.mova.domain.repositories.AccountRepository
import javax.inject.Inject

class UpdateSubscriptionStatusUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(hasSubscription: Boolean) {
        accountRepository.setSubscriptionStatus(hasSubscription)
    }
}