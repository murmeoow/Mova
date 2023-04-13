package dm.sample.mova.domain.usecase.explore

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.response.GetMovieListResponse
import dm.sample.mova.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {

    suspend operator fun invoke(
        page: Int,
        searchText: String,
    ): Flow<Resource<GetMovieListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = if (searchText.isNotBlank()) {
                movieRepository.searchMovie(page, searchText)
            } else { // if search text  empty load
                movieRepository.searchMovieByFilters(page, listOf())
            }
            emit(Resource.Success(response))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }
}