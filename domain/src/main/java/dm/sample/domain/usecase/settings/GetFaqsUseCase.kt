package dm.sample.mova.domain.usecase.settings

import dm.sample.mova.domain.base.BaseException
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Faq
import dm.sample.mova.domain.entities.FaqCategory
import dm.sample.mova.domain.repositories.FaqRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFaqsUseCase @Inject constructor(
    private val faqRepository: FaqRepository,
) {

    suspend operator fun invoke(
        keyword: String,
        categories: List<FaqCategory>,
    ): Flow<Resource<List<Faq>>> = flow {
        if (keyword.isEmpty()) {
            emit(Resource.Loading())
        }
        try {
            val faqs = faqRepository.fetchFaq(keyword, categories)
            emit(Resource.Success(faqs))
        } catch (e: BaseException) {
            emit(Resource.Error(e))
        }
    }

}