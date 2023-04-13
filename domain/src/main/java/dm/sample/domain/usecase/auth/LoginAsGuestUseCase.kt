package dm.sample.mova.domain.usecase.auth

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.response.CreateSessionResponse
import dm.sample.mova.domain.enums.LoginType
import dm.sample.mova.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginAsGuestUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() : Flow<Resource<CreateSessionResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authRepository.createSessionAsGuest()
            authRepository.setLoginType(LoginType.GUEST_LOGIN)
            emit(Resource.Success(response))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }
}