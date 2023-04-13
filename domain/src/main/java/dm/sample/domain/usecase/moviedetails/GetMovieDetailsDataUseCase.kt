package dm.sample.mova.domain.usecase.moviedetails

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.response.GetMovieDetailsResponse
import dm.sample.mova.domain.repositories.MovieRepository
import dm.sample.mova.domain.repositories.MyListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailsDataUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val myListRepository: MyListRepository
) {
    suspend operator fun invoke(movieId: Int): Flow<Resource<GetMovieDetailsResponse>> = flow {
        emit(Resource.Loading())
        try {
            val details = movieRepository.getMovieDetails(movieId)
            val isInFavourite = myListRepository.checkIsMovieInFavourite(movieId)
            emit(Resource.Success(details.copy(isInFavourite = isInFavourite.isChecked)))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }
}