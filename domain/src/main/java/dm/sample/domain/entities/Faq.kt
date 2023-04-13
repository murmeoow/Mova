package dm.sample.mova.domain.entities

data class Faq(
    val question: String,
    val answer: String,
    val category: List<FaqCategory>
)

data class FaqCategory(
    val id: Int,
    val name: String,
)
