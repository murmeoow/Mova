package dm.sample.mova.domain.usecase.account

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Account
import dm.sample.mova.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() : Flow<Resource<Account>> = flow {
        emit(Resource.Loading())
        try {
            val account = accountRepository.getAccount()
            emit(Resource.Success(account))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }

}