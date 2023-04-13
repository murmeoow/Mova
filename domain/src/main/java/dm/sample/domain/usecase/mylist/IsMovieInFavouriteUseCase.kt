package dm.sample.mova.domain.usecase.mylist

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.response.CheckIsMovieInMyListResponse
import dm.sample.mova.domain.repositories.MyListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsMovieInFavouriteUseCase @Inject constructor(
    private val myListRepository: MyListRepository
) {

    suspend operator fun invoke(movieId: Int) : Flow<Resource<CheckIsMovieInMyListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = myListRepository.checkIsMovieInFavourite(movieId)
            emit(Resource.Success(response))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }
}