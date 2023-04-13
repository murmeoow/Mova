package dm.sample.mova.domain.usecase.explore

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Filter
import dm.sample.mova.domain.entities.response.GetMovieListResponse
import dm.sample.mova.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMovieWithFiltersUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(
        page: Int,
        filters: List<Filter>,
    ): Flow<Resource<GetMovieListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = movieRepository.searchMovieByFilters(page, filters)
            emit(Resource.Success(response))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }

}