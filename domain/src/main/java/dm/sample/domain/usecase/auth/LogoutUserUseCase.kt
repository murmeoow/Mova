package dm.sample.mova.domain.usecase.auth

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.response.DeleteSessionResponse
import dm.sample.mova.domain.repositories.AuthRepository
import dm.sample.mova.domain.repositories.DownloadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val downloadRepository: DownloadRepository
) {

    suspend operator fun invoke(): Flow<Resource<DeleteSessionResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = authRepository.logoutUser()
            downloadRepository.deleteAllDownloadedMovies()
            emit(Resource.Success(response))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }
}