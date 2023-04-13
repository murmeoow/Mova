package dm.sample.mova.domain.usecase.notification

import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.enums.LoginType
import dm.sample.mova.domain.repositories.AuthRepository
import dm.sample.mova.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class IsMoviesChangeUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(): Flow<Resource<Boolean>> = flow {
        try {
            // fetch changes from api
            val loginType = authRepository.getLoginType()
            if (loginType == LoginType.GUEST_LOGIN) {
                emit(Resource.Success(false))
            } else {
                val response = movieRepository.movieChanges().take(Constants.MOVIE_CHANGE_LIST_LIMIT)
                    .sortedBy { it.id }
                // fetch changes from local db
                val localMovieChanges = movieRepository.localMovieChanges(LocalDate.now())
                    .take(Constants.MOVIE_CHANGE_LIST_LIMIT)
                    .sortedBy { it.id }

                val isEqual = response != localMovieChanges
                emit(Resource.Success(isEqual))
            }
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }

}
