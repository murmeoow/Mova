package dm.sample.mova.domain.usecase.genres

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.response.GenreResponse
import dm.sample.mova.domain.repositories.GenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genreRepository: GenreRepository
) {

    suspend operator fun invoke() : Flow<Resource<GenreResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = genreRepository.getGenresFlow()
            emit(Resource.Success(response))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }

}