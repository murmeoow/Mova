package dm.sample.mova.domain.usecase.account

import dm.sample.mova.domain.entities.Account
import dm.sample.mova.domain.repositories.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    
    /**
     * @param account data for update
     * @return updated Account
     */
    suspend operator fun invoke(account: Account) : Account = accountRepository.updateAccount(account)

}