package dm.sample.mova.domain.usecase.home

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.repositories.MyListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddRemoveToMyListUseCase @Inject constructor(
    private val myListRepository: MyListRepository
) {
    suspend operator fun invoke(movieId: Long, isAdded: Boolean): Flow<Resource<Boolean>> = flow {
        try {
            val status = if (isAdded) {
                !myListRepository.removeMovieFromMyList(movieId)
            } else {
                myListRepository.addMovieToMyList(movieId.toInt())
            }
            emit(Resource.Success(status))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }
}