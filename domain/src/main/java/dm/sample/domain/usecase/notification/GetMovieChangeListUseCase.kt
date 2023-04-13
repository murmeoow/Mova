package dm.sample.mova.domain.usecase.notification

import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.MovieChange
import dm.sample.mova.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class GetMovieChangeListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {

    suspend operator fun invoke() : Flow<Resource<List<MovieChange>>> = flow{
        try {
            // fetch changes from api
            val response = movieRepository.movieChanges()
            val changes = response.take(Constants.MOVIE_CHANGE_LIST_LIMIT)

            // save local changes if need
            val localMovieChanges = movieRepository.localMovieChanges(LocalDate.now()).take(Constants.MOVIE_CHANGE_LIST_LIMIT)
            if (localMovieChanges.isEmpty()) {
                movieRepository.saveLocalMovieChanges(changes)
            }

            emit(Resource.Success(changes))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }

}
