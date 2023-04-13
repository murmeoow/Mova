package dm.sample.mova.domain.usecase.auth

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.response.CreateSessionResponse
import dm.sample.mova.domain.enums.LoginType
import dm.sample.mova.domain.repositories.AccountRepository
import dm.sample.mova.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(username: String, password: String) : Flow<Resource<CreateSessionResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authRepository.createSessionAsUser(username, password)
            try {
                val accountDetails = accountRepository.fetchAccountDetails()
                accountRepository.getCreatedList(accountDetails.id)
                authRepository.setLoginType(LoginType.STANDARD_LOGIN)
                emit(Resource.Success(response))
            } catch (e: BaseException) {
                authRepository.logoutUser()
                emit(Resource.Error(e))
            }
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }
}