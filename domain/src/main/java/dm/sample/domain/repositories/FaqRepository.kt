package dm.sample.mova.domain.repositories

import dm.sample.mova.domain.entities.Faq
import dm.sample.mova.domain.entities.FaqCategory

interface FaqRepository {

    suspend fun fetchFaqCategories() : List<FaqCategory>

    suspend fun fetchFaq(
        keyword: String,
        categories: List<FaqCategory>,
    ) : List<Faq>
}