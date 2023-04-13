package dm.sample.mova.domain.usecase.mylist

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.repositories.MyListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveFromMyListUseCase @Inject constructor(
    private val myListRepository: MyListRepository
) {

    suspend operator fun invoke(movieId: Long) : Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val response = myListRepository.removeMovieFromMyList(movieId)
            emit(Resource.Success(response))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }
}