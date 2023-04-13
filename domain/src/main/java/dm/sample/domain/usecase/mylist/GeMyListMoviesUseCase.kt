package dm.sample.mova.domain.usecase.mylist

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.repositories.MyListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeMyListMoviesUseCase @Inject constructor(
    private val myListRepository: MyListRepository
) {
    suspend operator fun invoke() : Flow<Resource<List<Movie>?>> = flow {
        emit(Resource.Loading())
        try {
            val response = myListRepository.getMyListDetails()
            emit(Resource.Success(response))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }
}