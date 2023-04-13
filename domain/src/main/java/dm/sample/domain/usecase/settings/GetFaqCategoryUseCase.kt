package dm.sample.mova.domain.usecase.settings

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.FaqCategory
import dm.sample.mova.domain.repositories.FaqRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFaqCategoryUseCase @Inject constructor(
    private val faqRepository: FaqRepository,
) {

    suspend operator fun invoke(): Flow<Resource<List<FaqCategory>>> = flow {
        emit(Resource.Loading())
        try {
            val categories = faqRepository.fetchFaqCategories()
            emit(Resource.Success(categories))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }

}